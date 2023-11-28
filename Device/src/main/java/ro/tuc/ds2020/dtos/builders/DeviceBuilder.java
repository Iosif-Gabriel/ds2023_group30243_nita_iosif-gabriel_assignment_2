package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.MapperDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.MapperDeviceUser;
import ro.tuc.ds2020.services.MapperService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeviceBuilder {

    private static MapperService mapperService;
    private DeviceBuilder(MapperService mapperService) {
        DeviceBuilder.mapperService =mapperService;
    }

    public static DeviceDTO toDeviceDTO(Device device) {
        return new DeviceDTO(device.getId(), device.getOwnerId().getId());
    }

    public static DeviceDTO toDeviceDetailsDTO(Device device) {
        UUID ownerId = (device.getOwnerId() != null) ? device.getOwnerId().getId() : null;
        return new DeviceDTO(device.getId(), device.getAddress(), device.getDescription(), device.getMaxHConsumption(),ownerId);
    }

    public static Device toEntity(DeviceDTO deviceDTO) {
       MapperDTO mapperDeviceUser=new MapperDTO(deviceDTO.getOwnerId(),new ArrayList<>());
        mapperDeviceUser.setUserID(deviceDTO.getOwnerId());
        return new Device(deviceDTO.getId(), deviceDTO.getAddress(), deviceDTO.getDescription(), deviceDTO.getMaxHConsumption(),MapperBuilder.toEntity(mapperDeviceUser));
    }
}
