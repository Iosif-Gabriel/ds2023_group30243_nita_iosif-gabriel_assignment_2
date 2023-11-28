package ro.tuc.ds2020.entities;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "device")
public class Device implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name="description")
    private String description;

    @Column(name="address")
    private String address;

    @Column(name="maxHConsumption")
    private int maxHConsumption;

    @ManyToOne
    @JoinColumn(name = "ownerId")
    private MapperDeviceUser ownerId;

    public Device() {

    }

    public Device(UUID id, String description, String address, int maxHConsumption, MapperDeviceUser ownerId) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.maxHConsumption = maxHConsumption;
        this.ownerId = ownerId;
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

    public MapperDeviceUser getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(MapperDeviceUser ownerId) {
        this.ownerId = ownerId;
    }
}
