package com.codeMaster.codeMasterAPI.dto.Submission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionRequest {
    private Long problem_id;
    private String codeLanguage;
    private String code;
}
