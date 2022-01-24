package com.codeMaster.codeMasterAPI.service;

import com.codeMaster.codeMasterAPI.dto.Submission.CodeExecutionRequest;
import com.codeMaster.codeMasterAPI.dto.Submission.SubmissionRequest;
import com.codeMaster.codeMasterAPI.enums.Verdict;
import com.codeMaster.codeMasterAPI.models.CodeExecutionDetails;
import com.codeMaster.codeMasterAPI.models.Problem;
import com.codeMaster.codeMasterAPI.models.Submission;
import com.codeMaster.codeMasterAPI.repository.SubmissionRepository;
import com.codeMaster.codeMasterAPI.service.auth.UserDetailsServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class SubmissionService {
    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    ProblemService problemService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Value("${spring.rabbitmq.exchange_name}")
    private String EXCHANGE;

    @Value("${spring.rabbitmq.routing_key}")
    private String ROUTING_KEY;

    public void execute(@NotNull SubmissionRequest submission) {
        Optional<Problem> problem = problemService.find(submission.getProblem_id());
        if(problem.isPresent()){
            CodeExecutionRequest request = CodeExecutionRequest.builder()
                    .code(submission.getCode())
                    .codeLanguage(submission.getCodeLanguage())
                    .testCases(problem.get().getTestCases())
                    .timeLimit(problem.get().getTimeLimit())
                    .build();
            Submission submission1=new Submission();
            submission1.setCode(submission.getCode());
            submission1.setCodeLanguage(submission.getCodeLanguage());
            submission1.setSolvedBy(userDetailsService.getActiveUser().get());
            submission1.setProblem(problemService.find(submission.getProblem_id()).get());
            submission1.setCodeExecutionDetails(Collections.singletonList(new CodeExecutionDetails()));
            submissionRepository.save(submission1);
            rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, request);
//            return ResponseEntity.ok();
        }
//        else{
//            return ResponseEntity.notFound();
//        }

    }

    public Optional<Verdict> getStatus(Long submisson_id) {
        Optional<Submission> submission=submissionRepository.findById(submisson_id);
        return null;
    }
}
