package com.codeMaster.codeMasterAPI.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TestCase {
    @Id
    @GeneratedValue
    private Long testCase_id;

    private String input;
    @NotEmpty
    private String expectedOutput;

    public Long getTestCase_id() {
        return testCase_id;
    }

    public void setTestCase_id(Long testCase_id) {
        this.testCase_id = testCase_id;
    }

    public boolean passes(String actualOutput){
        return this.expectedOutput.equals(actualOutput);
    }

}
