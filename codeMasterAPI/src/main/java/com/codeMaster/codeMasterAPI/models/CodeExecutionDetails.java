package com.codeMaster.codeMasterAPI.models;

import com.codeMaster.codeMasterAPI.enums.Verdict;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CodeExecutionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;


    private Long executionTime=0L;

    private Verdict verdict=Verdict.QUEUED;

    private String output;

    @ManyToOne
    @JoinColumn(name = "test_case_test_case_id")
    private TestCase testCase;

}
