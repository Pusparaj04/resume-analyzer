package com.resumeanalyzer.resume_analyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CombinedResumeAnalysisResponseDTO {
    private JobMatchResponseDTO jobMatchResponseDTO;
    private GeminiAnalysisResponseDTO geminiAnalysisResponseDTO;
}
