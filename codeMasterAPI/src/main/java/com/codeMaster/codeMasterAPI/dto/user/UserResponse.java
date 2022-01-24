package com.codeMaster.codeMasterAPI.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Index;
import javax.persistence.Table;


@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class UserResponse {

    private String email;
    private String fullName;


}
