package com.codeMaster.codeMasterAPI.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class CodeMasterException extends RuntimeException {

    private final String message = null;
    private final HttpStatus httpStatus = null;

}