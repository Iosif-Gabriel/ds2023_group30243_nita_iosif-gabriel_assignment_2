package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.MapperDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.dtos.builders.MapperBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.MapperDeviceUser;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.MapperRepository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class MapperService {

    private final MapperRepository mapperRepository;

    private final DeviceRepository deviceRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);

    @Autowired
    public MapperService(MapperRepository mapperRepository, DeviceRepository deviceRepository){
        this.mapperRepository=mapperRepository;
        this.deviceRepository=deviceRepository;
    }

    public List<MapperDTO> findAllDevices(){
        List<MapperDeviceUser> devices=mapperRepository.findAll();

        return devices.stream().map(MapperBuilder::toMapperDTO).collect(Collectors.toList());
    }

    @Transactional
    public List<DeviceDTO> getDevicesForUser(UUID ownerId) {
        MapperDeviceUser user = mapperRepository.findById(ownerId).orElse(null);
        if (user != null) {

            List<Device> devices = user.getDevices();
            List<DeviceDTO> deviceDTOS=new ArrayList<>();
            for(Device d: devices){
               deviceDTOS.add( DeviceBuilder.toDeviceDetailsDTO(d));
            }

            return deviceDTOS;
        }
        return Collections.emptyList();
    }


    public UUID insert(UUID uuid) {
        MapperDeviceUser mapperDeviceUser = new MapperDeviceUser(uuid,null);
        mapperDeviceUser = mapperRepository.save(mapperDeviceUser);
        LOGGER.debug("Person with id {} was inserted in db", mapperDeviceUser.getId());
        return mapperDeviceUser.getId();
    }

    public MapperDTO updateMapper(UUID id, MapperDTO mapperDeviceUser) {
        Optional<MapperDeviceUser> personJr = mapperRepository.findById(id);
        if (personJr.isPresent()) {
            mapperRepository.save(MapperBuilder.toEntity(mapperDeviceUser));
        }
        return mapperDeviceUser;
    }

    @Transactional
    public UUID deleteMappingByUserId(UUID id) {
        mapperRepository.deleteMapperDeviceUserByOwnerId(id);
        return id;
    }


    public MapperDeviceUser getUser(UUID id){
        Optional<MapperDeviceUser> mapperDeviceUser=mapperRepository.findById(id);

        return mapperDeviceUser.orElse(null);

    }

}
