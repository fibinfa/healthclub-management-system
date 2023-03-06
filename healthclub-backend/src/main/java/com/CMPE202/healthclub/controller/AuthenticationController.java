package com.CMPE202.healthclub.controller;


import com.CMPE202.healthclub.model.AuthenticationRequest;
import com.CMPE202.healthclub.model.AuthenticationResponse;
import com.CMPE202.healthclub.model.UserModel;
import com.CMPE202.healthclub.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody @Valid UserModel userModel) {
        return new ResponseEntity(authService.registerUser(userModel), HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody @Valid AuthenticationRequest authRequest) {
        AuthenticationResponse authenticationResponse = authService.authenticate(authRequest);
        return ResponseEntity.ok(authenticationResponse);
    }

}
