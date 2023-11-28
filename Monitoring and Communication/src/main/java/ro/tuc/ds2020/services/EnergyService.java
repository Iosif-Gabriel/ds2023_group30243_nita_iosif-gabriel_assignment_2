package ro.tuc.ds2020.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.EnergyDTO;
import ro.tuc.ds2020.dtos.builders.EnergyBuilder;
import ro.tuc.ds2020.entities.EnergyConsumption;
import ro.tuc.ds2020.repositories.EnergyRepository;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EnergyService {

    private final EnergyRepository energyRepository;

    private SimpMessagingTemplate messagingTemplate;


    @Autowired
    public EnergyService(EnergyRepository energyRepository , SimpMessagingTemplate messagingTemplate) {
        this.energyRepository = energyRepository;
        this.messagingTemplate= messagingTemplate;
    }

    public UUID insertConsumption(EnergyDTO energyDTO){
        EnergyConsumption energyConsumption= EnergyBuilder.toEntity(energyDTO);

        energyConsumption=energyRepository.save(energyConsumption);

        return energyConsumption.getId();
    }

    public List<EnergyDTO> getAllConsumptions(){
        List<EnergyConsumption> consumptions=energyRepository.findAll();

        return consumptions.stream().map(EnergyBuilder::toEnergyDTO).collect(Collectors.toList());
    }

    public List<EnergyDTO> getConsumptionByDeviceId(UUID deviceId){
        List<EnergyConsumption> consumptions=energyRepository.findByDeviceId(deviceId);

        return consumptions.stream().map(EnergyBuilder::toEnergyDTO).collect(Collectors.toList());
    }

    public void deleteEnergyByDeviceID(UUID deviceId){
        energyRepository.deleteAllByDeviceId(deviceId);
    }



    public String checkHourlyEnergyLimit(DeviceDTO deviceDTO) {

        UUID deviceId = deviceDTO.getId();
        List<EnergyDTO> consumptionList = getConsumptionByDeviceId(deviceId);
        int maximumEnergyLimit = deviceDTO.getMaxHConsumption();

        List<EnergyDTO> lastHourConsumption = filterConsumptionByLastHour(consumptionList);

        double cumulativeEnergyLastHour = calculateCumulativeEnergy(lastHourConsumption);
        System.out.println(cumulativeEnergyLastHour);
        if (cumulativeEnergyLastHour > maximumEnergyLimit) {

            return "Cumulative energy for the last hour exceeds the maximum limit for device " + deviceDTO.getDescription();

        } else {


            return "Cumulative energy for the last hour is within the limit for device " + deviceDTO.getDescription();
        }

    }




    private List<EnergyDTO> filterConsumptionByLastHour(List<EnergyDTO> consumptionList) {
        long oneHourAgo = Instant.now().minusSeconds(3600).toEpochMilli();

        return consumptionList.stream()
                .filter(entry -> entry.getTimestamp().getTime() >= oneHourAgo)
                .toList();
    }
    private double calculateCumulativeEnergy(List<EnergyDTO> consumptionList) {

        return consumptionList.stream()
                .mapToDouble(EnergyDTO::getMeasurementValue)
                .sum();
    }


    private void resetCumulativeEnergy(UUID deviceId) {
        //TO DO
    }
}
