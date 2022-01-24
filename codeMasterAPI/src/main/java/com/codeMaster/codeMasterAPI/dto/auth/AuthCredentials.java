package com.codeMaster.codeMasterAPI.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthCredentials {
    String authenticationToken;
    String refreshToken;
    Instant expiresAt;
}
