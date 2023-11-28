package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;


import javax.persistence.Column;
import java.util.List;
import java.util.UUID;

public class DeviceDTO extends RepresentationModel<DeviceDTO> {

    private UUID id;


    private String description;


    private String address;


    private int maxHConsumption;


    private UUID ownerId;

    public DeviceDTO(UUID id, String description, String address, int maxHConsumption, UUID ownerId) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.maxHConsumption = maxHConsumption;
        this.ownerId = ownerId;
    }

    public DeviceDTO(UUID id, UUID ownerId) {
        this.id = id;
        this.ownerId = ownerId;
    }

    public DeviceDTO(){

    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMaxHConsumption() {
        return maxHConsumption;
    }

    public void setMaxHConsumption(int maxHConsumption) {
        this.maxHConsumption = maxHConsumption;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "DeviceDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", maxHConsumption=" + maxHConsumption +
                ", ownerId=" + ownerId +
                '}';
    }
}
