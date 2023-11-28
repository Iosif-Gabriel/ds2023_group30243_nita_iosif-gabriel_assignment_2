package ro.tuc.ds2020.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.socket.WebSocketSession;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.services.EnergyService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@CrossOrigin("http://localhost:4200")
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private Map<UUID, List<DeviceDTO>> deviceStorage;
    private final EnergyService energyService;
    private final Map<String, String> userSessions;

    public WebSocketController(SimpMessagingTemplate messagingTemplate,EnergyService energyService) {
        this.messagingTemplate = messagingTemplate;
        this.deviceStorage = new HashMap<>();
        this.energyService=energyService;
        this.userSessions= new ConcurrentHashMap<String, String>();
    }

    @MessageMapping("/connect/{userId}")
    public String connect(@PathVariable String userId, SimpMessageHeaderAccessor headerAccessor) {

        userSessions.put(userId, headerAccessor.getSessionId());
        //System.out.println(userSessions);
        return "Connected!";
    }

    @MessageMapping("/disconnect/{userId}")
    public String disconnect(@PathVariable String userId, SimpMessageHeaderAccessor headerAccessor) {
        //System.out.println("disc"+headerAccessor.getSessionId());
        userSessions.remove(userId);
        deviceStorage.remove(UUID.fromString(userId));
        return "Disconnected!";
    }



    @MessageMapping("/sendDevices/{userId}")
    @SendTo("/topic/notifications/{userId}")
    public void sendDevices(@Payload List<DeviceDTO> devices, @DestinationVariable String userId) {
        String sessionId = userSessions.get(userId);
        Set<UUID> ownerIds = new HashSet<>();

        for (DeviceDTO device : devices) {
            UUID ownerId = device.getOwnerId();
            ownerIds.add(ownerId);

            String ownerSessionId = userSessions.get(ownerId.toString());

            if (ownerSessionId != null && ownerSessionId.equals(sessionId)) {
                String message = energyService.checkHourlyEnergyLimit(device);
                messagingTemplate.convertAndSend("/topic/check2/"+userId, message);
            } else {
                System.out.println("User with id " + ownerId + " is not connected or not authorized.");
            }
        }

        for (UUID ownerId : ownerIds) {
            List<DeviceDTO> userDevices = deviceStorage.computeIfAbsent(ownerId, k -> new ArrayList<>());

            for (DeviceDTO device : devices) {
                // Check if the device is not already present in the user's list
                if (!userDevices.contains(device)) {
                    userDevices.add(device);
                }
            }
        }
    }





    @Scheduled(fixedRate = 50000)
    public void sendPeriodicMessage() {
        for (Map.Entry<UUID, List<DeviceDTO>> entry : deviceStorage.entrySet()) {
            UUID ownerId = entry.getKey();
            String sessionId = userSessions.get(ownerId.toString());
            System.out.println(deviceStorage.entrySet());
            //System.out.println(sessionId);
            if (sessionId != null) {
                List<DeviceDTO> devices = entry.getValue();
                for (DeviceDTO device : devices) {
                    String message = energyService.checkHourlyEnergyLimit(device);
                    System.out.println(message);
                    messagingTemplate.convertAndSend("/topic/check/"+ownerId, message);
                    //messagingTemplate.convertAndSendToUser(ownerId.toString(), "/topic/check", message);
                }
            } else {

                System.out.println("User with id " + ownerId + " is not connected.");
            }
        }
    }



}
