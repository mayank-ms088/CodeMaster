package com.codeMaster.codeMasterAPI.service;

import com.codeMaster.codeMasterAPI.models.Problem;
import com.codeMaster.codeMasterAPI.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProblemService {
    @Autowired
    private ProblemRepository problemRepository;

    public void add(List<Problem> problemList) {
        problemRepository.saveAll(problemList);
    }


    public List<Problem> findAll() {
        return problemRepository.findAll();
    }

    public Optional<Problem> find(long problem_id) {
        return problemRepository.findById(problem_id);
    }

    public void update(List<Problem> problem) {
        problemRepository.saveAll(problem);
    }
}
