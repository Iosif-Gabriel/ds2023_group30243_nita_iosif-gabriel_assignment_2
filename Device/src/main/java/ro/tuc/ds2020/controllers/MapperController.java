package ro.tuc.ds2020.controllers;

import org.hibernate.type.UUIDBinaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.MapperDTO;
import ro.tuc.ds2020.repositories.MapperRepository;
import ro.tuc.ds2020.services.DeviceService;
import ro.tuc.ds2020.services.MapperService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(value = "/mapper")
public class MapperController {

    private final MapperService mapperService;
    private final DeviceService deviceService;
    @Autowired
    public MapperController(MapperService mapperService, DeviceService deviceService){
        this.mapperService=mapperService;
        this.deviceService=deviceService;
    }

    @GetMapping()
    public ResponseEntity<String> hello(){
        String n="hello";
        return new ResponseEntity<>(n,HttpStatus.ACCEPTED);
    }


    @PostMapping("/insertMapping/{id}")
    public ResponseEntity<UUID> insertMapping(@Valid @PathVariable("id")  UUID uuid) {
        System.out.println(uuid);
        UUID personID = mapperService.insert(uuid);
        return new ResponseEntity<>(personID, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteMapping/{id}")
    public ResponseEntity<UUID> deleteUser(@PathVariable UUID id) {
        deviceService.removeOwnerIdFromDevices(id);
        mapperService.deleteMappingByUserId(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PostMapping("/updateOwner/{id}")
    public ResponseEntity<String> updateDeviceOwner(@PathVariable UUID id) {
        System.out.println("Received device owner update request for ID: " + id);

        String responseMessage = "Device owner updated successfully";

        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }


}
