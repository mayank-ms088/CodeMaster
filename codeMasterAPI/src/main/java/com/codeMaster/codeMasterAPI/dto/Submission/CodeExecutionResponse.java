package com.codeMaster.codeMasterAPI.dto.Submission;

import com.codeMaster.codeMasterAPI.enums.Verdict;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeExecutionResponse {
    private long executionTime;
    private String output;
    private Verdict verdict;
}
