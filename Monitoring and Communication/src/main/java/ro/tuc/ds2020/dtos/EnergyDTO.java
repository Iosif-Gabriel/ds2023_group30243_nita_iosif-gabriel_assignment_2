package ro.tuc.ds2020.dtos;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.util.UUID;

public class EnergyDTO {
    private UUID id;


    private Timestamp timestamp;


    private UUID deviceId;


    private Double measurementValue;

    public EnergyDTO(UUID id, Timestamp timestamp, UUID deviceId, Double measurementValue) {
        this.id = id;
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.measurementValue = measurementValue;
    }

    public EnergyDTO( Timestamp timestamp, UUID deviceId, Double measurementValue) {

        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.measurementValue = measurementValue;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public UUID getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(UUID deviceId) {
        this.deviceId = deviceId;
    }

    public Double getMeasurementValue() {
        return measurementValue;
    }

    public void setMeasurementValue(Double measurementValue) {
        this.measurementValue = measurementValue;
    }
}
