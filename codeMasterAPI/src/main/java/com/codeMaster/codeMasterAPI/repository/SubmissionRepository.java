package com.codeMaster.codeMasterAPI.repository;

import com.codeMaster.codeMasterAPI.models.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission,Long> {
}
