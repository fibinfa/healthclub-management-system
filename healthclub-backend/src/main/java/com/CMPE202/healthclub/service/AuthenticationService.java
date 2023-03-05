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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registerUser(UserModel userModel) {
        User user = new User();
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setRole(ROLE.USER);
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authRequest) {
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       authRequest.getEmail(),
                       authRequest.getPassword()
               )
       );
       var user = userRepository.
               findUserByEmail(authRequest.getEmail())
               .orElseThrow(() -> new IllegalArgumentException("Email and password do not match"));

        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
