package com.resumeanalyzer.resume_analyzer.service;

import com.google.genai.Client;
import com.resumeanalyzer.resume_analyzer.dto.GeminiAnalysisResponseDTO;

public interface GeminiResumeAnalysisService {
     GeminiAnalysisResponseDTO analyzeResume(String resumeText, String jobDescription);
}
