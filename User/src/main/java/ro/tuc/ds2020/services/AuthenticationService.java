package ro.tuc.ds2020.services;

import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Adăugați BCryptPasswordEncoder
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.AuthenticationRequest;
import ro.tuc.ds2020.dtos.AuthenticationResponse;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.UserRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = repository.findUserByUsername(request.getUsername());

        if (user == null ||!Objects.equals(user.getPassword(), request.getPassword())) {
            throw new RuntimeException("Utilizatorul nu există.");
        }



        System.out.println(request.getUsername()+" "+request.getPassword());
        System.out.println(user.getUsername()+" "+user.getPassword());

        if (isOldPasswordFormat(request.getPassword(), user.getPassword())) {
            if (isPasswordMatching(request.getPassword(), user.getPassword())) {

                var jwtToken = jwtService.generateToken(user);
                return new AuthenticationResponse(jwtToken, user.getId());
            }
        }

        if (isPasswordMatching(request.getPassword(), user.getPassword())) {

            var jwtToken = jwtService.generateToken(user);
            return new AuthenticationResponse(jwtToken, user.getId());
        }

        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken, user.getId());
    }

    private boolean isOldPasswordFormat(String inputPassword, String storedPassword) {

        return inputPassword.equals(storedPassword);
    }

    private boolean isPasswordMatching(String inputPassword, String storedPassword) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(inputPassword, storedPassword);
    }
}
