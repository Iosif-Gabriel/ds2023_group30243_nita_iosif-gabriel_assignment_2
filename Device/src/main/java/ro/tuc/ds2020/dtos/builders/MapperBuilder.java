package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.MapperDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.MapperDeviceUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MapperBuilder {

    private MapperBuilder() {
    }

    public static MapperDTO toMapperDTO(MapperDeviceUser mapper) {
        List<Device> deviceList=mapper.getDevices();
        List<DeviceDTO> deviceDTO=new ArrayList<>();
        for(Device d:deviceList){
            deviceDTO.add(DeviceBuilder.toDeviceDetailsDTO(d));

        }
        return new MapperDTO(mapper.getId(),deviceDTO);
    }

//    public static MapperDTO toMapperDTO(MapperDeviceUser mapper) {
//        return new MapperDTO(device.getId(), device.getAddress(), device.getDescription(), device.getMaxHConsumption(),device.getOwnerId());
//    }

    public static MapperDeviceUser toEntity(MapperDTO mapperDTO) {
        List<Device> devices =new ArrayList<>();
        List<DeviceDTO> deviceDTO=mapperDTO.getDevices();

        for(DeviceDTO d:deviceDTO){
            devices.add(DeviceBuilder.toEntity(d));


        }
        return new MapperDeviceUser(mapperDTO.getUserID(),devices);
    }


}
