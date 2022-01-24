package com.codeMaster.codeMasterAPI.repository;

import com.codeMaster.codeMasterAPI.models.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem,Long> {
}
