package com.resumeanalyzer.resume_analyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class JobMatchResponseDTO {
    private float matchPercentage;
    private List<String> matchedKeywords;
    private List<String> missingKeywords;
}
