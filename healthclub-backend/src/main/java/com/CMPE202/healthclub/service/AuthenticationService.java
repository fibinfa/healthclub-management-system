package com.CMPE202.healthclub.service;


import com.CMPE202.healthclub.entity.ROLE;
import com.CMPE202.healthclub.entity.User;
import com.CMPE202.healthclub.model.AuthenticationRequest;
import com.CMPE202.healthclub.model.AuthenticationResponse;
import com.CMPE202.healthclub.model.UserModel;
import com.CMPE202.healthclub.repository.UserRepository;
import com.CMPE202.healthclub.security.service.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    public void registerUser(UserModel userModel) {
        // Check if email already exists
        String email = userModel.getEmail();
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if(optionalUser.isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        User user = User.builder()
                .email(userModel.getEmail())
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .role(userModel.getRole())
                .password(passwordEncoder.encode(userModel.getPassword()))
                .build();
        userRepository.save(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authRequest) {

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            throw new IllegalArgumentException("Invalid credentials");
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

       var user = userRepository.
               findUserByEmail(authRequest.getEmail())
               .orElseThrow(() -> new IllegalArgumentException("Email not found"));

        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
