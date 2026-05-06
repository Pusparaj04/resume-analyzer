package com.resumeanalyzer.resume_analyzer.repository;

import com.resumeanalyzer.resume_analyzer.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
