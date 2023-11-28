package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.services.DeviceService;
import ro.tuc.ds2020.services.MapperService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(value = "/device")
public class DeviceController {

    private final DeviceService deviceService;
    private final MapperService mapperDeviceUserService;

    @Autowired
    public DeviceController(DeviceService deviceService , MapperService mapperDeviceUserService){
        this.deviceService=deviceService;
        this.mapperDeviceUserService=mapperDeviceUserService;

    }

    @GetMapping("/getAllDevices")
    public ResponseEntity<List<DeviceDTO>> getDevice() {
        List<DeviceDTO> dtos = deviceService.findAllDevices();
//        for (DeviceDTO dto : dtos) {
//            Link personLink = linkTo(methodOn(DeviceController.class)
//                    .getDevice(dto.getId())).withRel("deviceDetails");
//            dto.add(personLink);
//        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/createDevice")
    public ResponseEntity<UUID> insertDevice(@Valid @RequestBody DeviceDTO deviceDTO) throws Exception {
        UUID deviceID = deviceService.insert(deviceDTO);
        System.out.println(deviceDTO);
        return new ResponseEntity<>(deviceID, HttpStatus.CREATED);
    }

    @GetMapping( "/getDeviceBy/{id}")
    public ResponseEntity<DeviceDTO> getDevice(@PathVariable("id") UUID deviceID) {
        DeviceDTO dto = deviceService.findDeviceById(deviceID);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<UUID> updateDeviceDetails(@PathVariable UUID id, @RequestBody DeviceDTO deviceDTO) {
        UUID updatedDeviceId = deviceService.updateDevice(id, deviceDTO).getId();
        return ResponseEntity.status(HttpStatus.OK).body(updatedDeviceId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UUID> deleteDevice(@PathVariable UUID id) {

        deviceService.deleteDeviceById(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @GetMapping("/getUserDevices/{id}")
    public ResponseEntity<List<DeviceDTO>> getUserDevices(@PathVariable("id") UUID userID){
        System.out.println(userID);
        List<DeviceDTO> deviceDTOS=deviceService.getUserDevice(userID);
        return new ResponseEntity<>(deviceDTOS, HttpStatus.OK);
    }

    @GetMapping("/getOwnerDevices/{ownerId}")
    public ResponseEntity<List<DeviceDTO>> getDevicesByOwnerId(@PathVariable UUID ownerId) {
        List<DeviceDTO> devices = mapperDeviceUserService.getDevicesForUser(ownerId);

        if (devices.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(devices);
        }
    }

}
