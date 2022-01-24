package com.codeMaster.codeMasterAPI.controllers;

import com.codeMaster.codeMasterAPI.dto.Submission.SubmissionRequest;
import com.codeMaster.codeMasterAPI.enums.Verdict;
import com.codeMaster.codeMasterAPI.models.Submission;
import com.codeMaster.codeMasterAPI.service.SubmissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/submissions")
@Slf4j
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;

    @PostMapping("/submit")
    public void submit(@RequestBody SubmissionRequest submission){
        submissionService.execute(submission);

    }

    @GetMapping("/status/{id}")
    public Object status(@PathVariable Long submisson_id){
        Optional<Verdict> status = submissionService.getStatus(submisson_id);
        if(status.isPresent()){
            return status.get().toString();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
