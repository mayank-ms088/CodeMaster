package com.codeMaster.codeMasterAPI.models;

import com.codeMaster.codeMasterAPI.dto.Submission.CodeExecutionResponse;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "submission_id", nullable = false)
    private Long submission_id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private User solvedBy;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Problem problem;

    @Column(length = 15000)
    private String code;

    @NotEmpty
    private String codeLanguage;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CodeExecutionDetails> codeExecutionDetails;



}
