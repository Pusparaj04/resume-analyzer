package com.resumeanalyzer.resume_analyzer.repository;

import com.resumeanalyzer.resume_analyzer.model.JobMatchAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobMatchAnalysisRepository extends JpaRepository<JobMatchAnalysis, Long> {
}
