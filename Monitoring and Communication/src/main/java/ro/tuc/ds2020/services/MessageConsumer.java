package ro.tuc.ds2020.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.EnergyDTO;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

@Service
@EnableRabbit
public class MessageConsumer {

    private final EnergyService energyService;
    @Autowired
    public MessageConsumer(EnergyService energyService) {
        this.energyService=energyService;
    }
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = "broker")
    public void receiveMessage(final String message) {
        try {
            Map messageMap = objectMapper.readValue(message, Map.class);
            String energyString = (String) messageMap.get("measurement_value");
            String deviceIdString = (String) messageMap.get("device_id");
            Long timestampLong = (Long) messageMap.get("timestamp");

            Double energyValue = Double.parseDouble(energyString);


            UUID deviceId = UUID.fromString(deviceIdString);


            Timestamp timestamp2 = new Timestamp(timestampLong);

            EnergyDTO energyDTO = new EnergyDTO(timestamp2, deviceId, energyValue);
            energyService.insertConsumption(energyDTO);
            System.out.println("Received: '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}