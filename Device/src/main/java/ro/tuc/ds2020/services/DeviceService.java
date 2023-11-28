package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.MapperDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.MapperDeviceUser;
import ro.tuc.ds2020.repositories.DeviceRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    private final MapperService mapperService;

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);

    @Autowired
    public DeviceService(DeviceRepository deviceRepository,MapperService mapperService,EntityManager entityManager){
        this.deviceRepository=deviceRepository;
        this.entityManager=entityManager;
        this.mapperService=mapperService;
    }

    public List<DeviceDTO> findAllDevices(){
        List<Device> devices=deviceRepository.findAll();

        return devices.stream().map(DeviceBuilder::toDeviceDetailsDTO).collect(Collectors.toList());
    }


    public DeviceDTO findDeviceById(UUID id) {
        Optional<Device> prosumerOptional = deviceRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", id);
            //throw new ResourceNotFoundException(Person.class.getSimpleName() + " with id: " + id);
        }
        return DeviceBuilder.toDeviceDetailsDTO(prosumerOptional.get());
    }

    @Transactional
    public UUID insert(DeviceDTO deviceDTO) throws Exception {
        Device device = DeviceBuilder.toEntity(deviceDTO);

        UUID ownerId = deviceDTO.getOwnerId();

        try {

            UUID user= mapperService.getUser(ownerId).getId();

            if (user != null) {
                Device managedDevice = entityManager.merge(device);
                deviceRepository.save(managedDevice);

                LOGGER.debug("Device with id {} was inserted in db", managedDevice.getId());
                return managedDevice.getId();
            } else {

                LOGGER.error("User with ownerId {} does not exist", ownerId);
                throw new Exception("User not found for ownerId: " + ownerId);
            }
        } catch (Exception e) {

            LOGGER.error("An error occurred while inserting the device", e);
            throw new Exception("Failed to insert the device", e);
        }
    }


    @Transactional
    public DeviceDTO updateDevice(UUID id, DeviceDTO deviceDetailsDTO) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);

        if (deviceOptional.isPresent()) {
            Device device = deviceOptional.get();

            if (deviceDetailsDTO.getDescription() != null) {
                device.setDescription(deviceDetailsDTO.getDescription());
            }
            if (deviceDetailsDTO.getAddress() != null) {
                device.setAddress(deviceDetailsDTO.getAddress());
            }
            if(deviceDetailsDTO.getMaxHConsumption()!=device.getMaxHConsumption()){
                device.setMaxHConsumption(deviceDetailsDTO.getMaxHConsumption());
            }
            device.setOwnerId(mapperService.getUser(deviceDetailsDTO.getOwnerId()));
            deviceRepository.save(device);

            return deviceDetailsDTO;
        } else {

            return null;
        }
    }


    @Transactional
    public void removeOwnerIdFromDevices(UUID userId) {
        List<Device> devices = deviceRepository.findDeviceByOwnerId_OwnerId(userId);

        for (Device device : devices) {
            device.setOwnerId(null); // Set the owner ID to null
        }

        deviceRepository.saveAll(devices);
    }





    @Transactional
    public UUID deleteDeviceById(UUID id) {
        deviceRepository.deleteById(id);
        return id;
    }

    @Transactional
    public List<DeviceDTO> getUserDevice(UUID userID){

        List<Device> devices=deviceRepository.findDeviceByOwnerId_OwnerId(userID);
        List<DeviceDTO> deviceDTOS=new ArrayList<>();
        for (Device device : devices){

            deviceDTOS.add(DeviceBuilder.toDeviceDetailsDTO(device));
        }
        return deviceDTOS;
    }
}
