package com.example.codeMasterEngine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCase {

    private Long testCase_id;

    private String input;
    private String expectedOutput;



}
