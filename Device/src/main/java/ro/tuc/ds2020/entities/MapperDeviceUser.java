package ro.tuc.ds2020.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="mapper")
public class MapperDeviceUser implements Serializable {



    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    @Column(name = "id")
    private UUID ownerId;

    @OneToMany(mappedBy = "ownerId")
    private List<Device> devices;



    public MapperDeviceUser() {

    }

    public MapperDeviceUser(UUID id, List<Device> devices) {
        this.ownerId = id;
        this.devices = devices;
    }

    public UUID getId() {
        return ownerId;
    }

    public void setId(UUID id) {
        this.ownerId = id;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
