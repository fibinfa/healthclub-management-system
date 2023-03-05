package com.CMPE202.healthclub.controller;


import com.CMPE202.healthclub.model.AuthenticationRequest;
import com.CMPE202.healthclub.model.AuthenticationResponse;
import com.CMPE202.healthclub.model.UserModel;
import com.CMPE202.healthclub.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody UserModel userModel) {
        return ResponseEntity.ok(authService.registerUser(userModel));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest authRequest) {
        AuthenticationResponse authenticationResponse = authService.authenticate(authRequest);
        return ResponseEntity.ok(authenticationResponse);
    }

}
