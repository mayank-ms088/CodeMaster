package com.codeMaster.codeMasterAPI.dto.Problem;

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
public class ProblemDetails {
    private long problem_id;
    private String title;
    private String description;
    private String inputDescription;
    private String outputDescription;
    private long timeLimit;
    List<TestCase> testCases;
}
