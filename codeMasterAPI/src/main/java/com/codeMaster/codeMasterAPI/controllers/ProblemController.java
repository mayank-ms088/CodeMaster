package com.codeMaster.codeMasterAPI.controllers;

import com.codeMaster.codeMasterAPI.dto.Problem.ProblemDetails;
import com.codeMaster.codeMasterAPI.models.Problem;
import com.codeMaster.codeMasterAPI.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/problems", consumes = "application/json", produces = "application/json")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @PostMapping("/add")
    public void add(@RequestBody List<Problem> problems){
        problemService.add(problems);
    }

    @GetMapping("/get")
    public List<ProblemDetails> findAll(){
        return problemService.findAll().stream().map(this::buildDto).collect(Collectors.toList());
    }

    @GetMapping("/get/{problem_id}")
    public ResponseEntity<ProblemDetails> find(@PathVariable long problem_id){
        Optional<Problem> problem = problemService.find(problem_id);
        return problem.map(value -> ResponseEntity.ok(buildDto(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/update")
    public void update(@RequestBody List<Problem> problem){
        problemService.update(problem);
    }

    private ProblemDetails buildDto(Problem que) {
        if(que==null) {
            return null;
        }
        return ProblemDetails.builder()
                .title(que.getTitle())
                .description(que.getDescription())
                .problem_id(que.getProblem_id())
                .testCases(que.getTestCases())
                .timeLimit(que.getTimeLimit())
                .outputDescription(que.getOutputDescription())
                .inputDescription(que.getInputDescription())
                .build();
    }

}
