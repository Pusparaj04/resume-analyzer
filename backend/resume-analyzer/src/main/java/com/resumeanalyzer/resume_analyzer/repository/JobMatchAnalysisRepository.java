package com.resumeanalyzer.resume_analyzer.repository;

import com.resumeanalyzer.resume_analyzer.model.JobMatchAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobMatchAnalysisRepository extends JpaRepository<JobMatchAnalysis, Long> {
}
