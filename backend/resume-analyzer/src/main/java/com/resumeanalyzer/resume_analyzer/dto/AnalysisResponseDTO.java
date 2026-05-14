package com.resumeanalyzer.resume_analyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AnalysisResponseDTO {
    private float score;
    private List<String> foundSkills;
    private List<String> missingSkills;
}
