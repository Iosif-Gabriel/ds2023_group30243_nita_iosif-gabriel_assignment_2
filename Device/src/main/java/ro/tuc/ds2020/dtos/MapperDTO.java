package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import ro.tuc.ds2020.entities.Device;

import javax.persistence.Column;
import java.util.List;
import java.util.UUID;

public class MapperDTO extends RepresentationModel<MapperDTO> {


    private UUID userID;

    private List<DeviceDTO> devices;


    public MapperDTO(UUID userID, List<DeviceDTO> devices) {
        this.userID = userID;
        this.devices = devices;
    }

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public List<DeviceDTO> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceDTO> devices) {
        this.devices = devices;
    }
}
