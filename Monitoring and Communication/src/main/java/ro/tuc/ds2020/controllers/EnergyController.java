package ro.tuc.ds2020.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event;
import ro.tuc.ds2020.dtos.EnergyDTO;
import ro.tuc.ds2020.services.EnergyService;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(value = "/energy")
public class EnergyController {

    private final EnergyService energyService;

    public EnergyController(EnergyService energyService) {
        this.energyService = energyService;
    }

    @GetMapping("/getAllConsumptions")
    public ResponseEntity<List<EnergyDTO>> getAllConsumptions(){
        List<EnergyDTO> consumption=energyService.getAllConsumptions();

        return new ResponseEntity<>(consumption, HttpStatus.OK);
    }

    @GetMapping("/getConsumptionByDeviceId/{id}")
    public ResponseEntity<List<EnergyDTO>> getConsumptionByDeviceId(@PathVariable UUID id){
        List<EnergyDTO> consumption=energyService.getConsumptionByDeviceId(id);

        return new ResponseEntity<>(consumption, HttpStatus.OK);
    }



}
