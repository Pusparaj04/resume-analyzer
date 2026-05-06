package com.resumeanalyzer.resume_analyzer.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface AiAnalysisService {
    List<String> SKILLS = Arrays.asList(
            "java", "spring boot", "sql", "python", "react", "docker"
    );

    Map<String, Object> resumeAnalyzer(String text);
}
