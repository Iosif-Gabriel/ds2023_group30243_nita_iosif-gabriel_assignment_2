package ro.tuc.ds2020.dtos.builders;


import ro.tuc.ds2020.dtos.EnergyDTO;
import ro.tuc.ds2020.entities.EnergyConsumption;



public class EnergyBuilder {

    private EnergyBuilder(){

    }

    public static EnergyDTO toEnergyDTO(EnergyConsumption energyConsumption) {

        return new EnergyDTO(energyConsumption.getId(),energyConsumption.getTimestamp(),energyConsumption.getDeviceId(),energyConsumption.getMeasurementValue());
    }

    public static EnergyConsumption toEntity(EnergyDTO energyConsumption) {

        return new EnergyConsumption(energyConsumption.getId(),energyConsumption.getTimestamp(),energyConsumption.getDeviceId(),energyConsumption.getMeasurementValue());
    }
}
