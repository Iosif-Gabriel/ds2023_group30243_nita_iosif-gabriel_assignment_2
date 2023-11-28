package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.UserDetailsDTO;
import ro.tuc.ds2020.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    private RestTemplate restTemplate;

    @Autowired
    public UserController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate=restTemplate;
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDetailsDTO>> getUsers(){
        List<UserDetailsDTO> users=userService.findAllUsers();
//        for (UserDTO dto : users) {
//            Link userLink = linkTo(methodOn(UserController.class)
//                    .getUser(dto.getId())).withRel("userDetails");
//            dto.add(userLink);
//        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/createUser")
    public ResponseEntity<UUID> insertPerson(@Valid @RequestBody UserDetailsDTO userDetailsDTO) {

        //String endpointUrl="http://"+ System.getenv("ALTADRESAIP") +":8081/mapper/insertMapping/28392a35-7acb-4108-bf72-223b1a107110";
        String endpointUrl="http://localhost:8081/mapper/insertMapping/28392a35-7acb-4108-bf72-223b1a107110";
        UUID user = restTemplate.postForObject(endpointUrl, userDetailsDTO, UUID.class);
        System.out.println(user);
        userDetailsDTO.setId(user);
        userService.insert(userDetailsDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }


    @GetMapping(value = "/getById/{id}")
    public ResponseEntity<UserDetailsDTO> getUser(@PathVariable("id") UUID personId) {
        UserDetailsDTO dto = userService.findUserById(personId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UUID> updateUserDetails(@PathVariable("id") UUID id, @RequestBody UserDetailsDTO userDetailsDTO) {
        UUID updatedUserId = userService.updateUser(id, userDetailsDTO).getId();
        return new ResponseEntity<>(updatedUserId, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<UUID> deleteUser(@PathVariable UUID id) {
        String endpointUrl="http://"+ System.getenv("ALTADRESAIP") +":8081/mapper/deleteMapping/"+id;
        restTemplate.delete(endpointUrl, id, UUID.class);
        userService.deletePersonById(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @GetMapping("/getUserByUser/{username}")
    public ResponseEntity<UUID> getUserByUsername(@PathVariable String username) throws NoSuchFieldException {
        UserDetailsDTO userDetailsDTO=userService.findUserByUsername(username);
        return new ResponseEntity<>(userDetailsDTO.getId(), HttpStatus.OK);
    }
}
