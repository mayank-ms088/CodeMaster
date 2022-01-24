package com.codeMaster.codeMasterAPI.dto.Submission;

import com.codeMaster.codeMasterAPI.models.TestCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeExecutionRequest {
    private String codeLanguage;
    private String code;
    private List<TestCase> testCases;
    private Long timeLimit;

}
