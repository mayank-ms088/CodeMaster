package com.codeMaster.codeMasterAPI.controllers;

import com.codeMaster.codeMasterAPI.dto.auth.AuthenticationResponse;
import com.codeMaster.codeMasterAPI.dto.auth.LoginRequest;
import com.codeMaster.codeMasterAPI.dto.auth.RefreshTokenRequest;
import com.codeMaster.codeMasterAPI.dto.auth.RegisterRequest;
import com.codeMaster.codeMasterAPI.service.auth.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

@RestController
@RequestMapping(value = "/auth", consumes = "application/json", produces = "application/json")
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest){
        authService.register(registerRequest);
        return new ResponseEntity<>("\"{\"message\": \"Registration Successful\"}\"", HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        log.info("logged in");
        return authService.login(loginRequest);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        authService.logout(refreshTokenRequest);
        return new ResponseEntity<>("\"{\\\"message\": \"Refresh Token Deleted\"}\"", HttpStatus.OK);
    }

    @PostMapping(value = "/refreshToken")
    public AuthenticationResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        return authService.refreshToken(refreshTokenRequest);
    }


}
