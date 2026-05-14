package com.resumeanalyzer.resume_analyzer.service;

import com.resumeanalyzer.resume_analyzer.dto.AnalysisResponseDTO;
import com.resumeanalyzer.resume_analyzer.dto.JobMatchResponseDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface AiAnalysisService {
    List<String> SKILLS = Arrays.asList(
            "java", "spring boot", "sql", "python", "react", "docker"
    );

    AnalysisResponseDTO resumeAnalyzer(String text);
    JobMatchResponseDTO matchJobDescription(String resumeText, String jobDescription);
}
