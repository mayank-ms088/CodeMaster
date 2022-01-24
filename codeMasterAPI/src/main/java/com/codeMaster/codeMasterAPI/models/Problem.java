package com.codeMaster.codeMasterAPI.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "problem_id", nullable = false)
    private Long problem_id;

    @NotEmpty
    @Size(max = 100, min = 5, message = "Title of question should not exceed more than 100 characters and should be at least 5 characters")
    private String title;

    @NotEmpty
    @Size(max = 10000, min = 50, message = "Description of question should not exceed more than 10000 characters and should be at least 50 characters")
    private String description;

    @NotEmpty
    @Column
    private String inputDescription;

    @NotEmpty
    @Column
    private String outputDescription;


    @NotNull
    @Column(name = "time_limit")
    private long timeLimit=2;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<TestCase> testCases;





}
