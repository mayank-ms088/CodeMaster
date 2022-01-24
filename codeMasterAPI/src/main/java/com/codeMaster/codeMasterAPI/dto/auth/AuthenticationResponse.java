package com.codeMaster.codeMasterAPI.dto.auth;

import com.codeMaster.codeMasterAPI.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {

    private AuthCredentials authCredentials;
    private UserResponse userResponse;

}
