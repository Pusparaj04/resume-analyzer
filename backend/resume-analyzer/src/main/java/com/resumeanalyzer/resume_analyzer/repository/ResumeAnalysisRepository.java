package com.resumeanalyzer.resume_analyzer.repository;

import com.resumeanalyzer.resume_analyzer.model.ResumeAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeAnalysisRepository extends JpaRepository<ResumeAnalysis, Long> {
}
