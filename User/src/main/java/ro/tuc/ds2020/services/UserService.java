package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.UserDetailsDTO;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.UserRepository;
import ro.tuc.ds2020.userrole.UserRole;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<UserDetailsDTO> findAllUsers(){
        List<User> users=userRepository.findAll();

        return users.stream().map(UserBuilder::toUserDetailsDTO).collect(Collectors.toList());
    }

    public UserDetailsDTO findUserById(UUID id) {
        Optional<User> prosumerOptional = userRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Person with id {} was not found in db", id);
            // throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
        }
        return UserBuilder.toUserDetailsDTO(prosumerOptional.get());
    }

    public UUID insert(UserDetailsDTO userDetailsDTO) {
        //String encodedPassword = bCryptPasswordEncoder.encode(userDetailsDTO.getPassword());
        User user = UserBuilder.toEntity(userDetailsDTO);
        //user.setPassword(encodedPassword);
        user = userRepository.save(user);
        LOGGER.debug("User with id {} was inserted in db", user.getId());
        return user.getId();
    }

    public UserDetailsDTO updateUser(UUID id, UserDetailsDTO userDetailsDTO) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (userDetailsDTO.getUsername() != null) {
                user.setUsername(userDetailsDTO.getUsername());
            }
            if (userDetailsDTO.getPassword() != null) {
                user.setPassword(userDetailsDTO.getPassword());
            }


            if (userDetailsDTO.getUserRole() != null &&
                    (userDetailsDTO.getUserRole() == UserRole.ADMIN || userDetailsDTO.getUserRole() == UserRole.USER)) {
                user.setUserRole(userDetailsDTO.getUserRole());
            }


            userRepository.save(user);
        }

        return userDetailsDTO;
    }



    public UUID deletePersonById(UUID id) {
        userRepository.deleteById(id);
        return id;
    }

    public UserDetailsDTO findUserByUsername(String username) throws NoSuchFieldException {
        try {
            User user = userRepository.findUserByUsername(username);
            System.out.println(user);
            if (user == null) {
                LOGGER.debug("User not found for username: " + username);
            }

            assert user != null;
            return UserBuilder.toUserDetailsDTO(user);
        } catch (Exception e) {

            throw new NoSuchFieldException();
        }
    }





}
