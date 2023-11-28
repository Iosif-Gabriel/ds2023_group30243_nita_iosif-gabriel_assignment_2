package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import ro.tuc.ds2020.entities.MapperDeviceUser;

import java.util.UUID;


public interface MapperRepository extends JpaRepository<MapperDeviceUser, UUID> {

    @Modifying
    void deleteMapperDeviceUserByOwnerId(UUID id);
}
