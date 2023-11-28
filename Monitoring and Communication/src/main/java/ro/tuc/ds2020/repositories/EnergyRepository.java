package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tuc.ds2020.entities.EnergyConsumption;

import java.util.List;
import java.util.UUID;

public interface EnergyRepository extends JpaRepository<EnergyConsumption, UUID> {

    List<EnergyConsumption> findByDeviceId(UUID deviceId);
    void deleteAllByDeviceId(UUID deviceId);
}
