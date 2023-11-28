package ro.tuc.ds2020.services;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.DeviceDTO;

import java.util.List;
import java.util.UUID;

@Service
@EnableRabbit
public class DeviceMessageConsumer {

    private final EnergyService energyService;
    @Autowired
    public DeviceMessageConsumer(EnergyService energyService) {
        this.energyService=energyService;
    }



    @RabbitListener(queues = "device-queue")
    public void receiveMessage(UUID deviceDTO) {

        energyService.deleteEnergyByDeviceID(deviceDTO);

        System.out.println("Received DTO: " + deviceDTO);
    }


//    @RabbitListener(queues = "device-queue")
//    public List<DeviceDTO> receiveMessage(List<DeviceDTO> deviceDTO) {
//
//        //energyService.checkHourlyEnergyLimit(deviceDTO);
//        System.out.println("Received DTO: " + deviceDTO);
//        return deviceDTO;
//    }
}
