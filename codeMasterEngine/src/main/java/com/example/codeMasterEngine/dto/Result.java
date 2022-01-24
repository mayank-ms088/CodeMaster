package com.example.codeMasterEngine.dto;

import com.example.codeMasterEngine.enums.Verdict;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String output;
    private Long executionTime=0L;
    private Verdict verdict=Verdict.QUEUED;
    private TestCase testCase;
}
