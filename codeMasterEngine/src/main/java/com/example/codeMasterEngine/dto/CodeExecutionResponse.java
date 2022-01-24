package com.example.codeMasterEngine.dto;

import com.example.codeMasterEngine.enums.Verdict;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeExecutionResponse {
    private boolean compilation_status;
    private List<Result> results;
}
