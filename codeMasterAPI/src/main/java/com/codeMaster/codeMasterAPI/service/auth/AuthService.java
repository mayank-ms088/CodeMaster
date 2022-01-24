package com.codeMaster.codeMasterAPI.service.auth;

import com.codeMaster.codeMasterAPI.dto.auth.*;
import com.codeMaster.codeMasterAPI.dto.user.UserResponse;
import com.codeMaster.codeMasterAPI.models.RefreshToken;
import com.codeMaster.codeMasterAPI.models.User;
import com.codeMaster.codeMasterAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.time.Instant;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RefreshTokenService refreshTokenService;

    public void register(@Valid RegisterRequest registerRequest) {
        User user=new User();
        user.setFullName(registerRequest.getFullName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.saveAndFlush(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String jwtToken = jwtProvider.generateToken(authenticate);
        RefreshToken refreshToken = refreshTokenService.generateToken();
        User user = findByEmail(loginRequest.getEmail());
        UserResponse userResponse=new UserResponse(user.getEmail(),user.getFullName());
        AuthCredentials authCredentials = AuthCredentials.builder()
                .authenticationToken(jwtToken)
                .expiresAt(Instant.now().plusSeconds(jwtProvider.getJwtExpirationSeconds()))
                .refreshToken(refreshToken.getToken())
                .build();

        return AuthenticationResponse.builder()
                .userResponse(userResponse)
                .authCredentials(authCredentials)
                .build();

    }

    private User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Details not available."));
    }

    public void logout(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteToken(refreshTokenRequest.getRefreshToken());
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        refreshTokenService.validateToken(refreshTokenRequest.getRefreshToken());
        String newJwtToken = jwtProvider.generateTokenWithEmail(refreshTokenRequest.getEmail());
        User user = findByEmail(refreshTokenRequest.getEmail());
        UserResponse userResponse=new UserResponse(user.getEmail(),user.getFullName());
        AuthCredentials authCredentials = AuthCredentials.builder()
                .authenticationToken(newJwtToken)
                .expiresAt(Instant.now().plusSeconds(jwtProvider.getJwtExpirationSeconds()))
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .build();

        return AuthenticationResponse.builder()
                .userResponse(userResponse)
                .authCredentials(authCredentials)
                .build();
    }
}
