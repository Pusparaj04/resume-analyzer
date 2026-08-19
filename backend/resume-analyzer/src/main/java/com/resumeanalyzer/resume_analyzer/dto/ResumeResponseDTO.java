package com.resumeanalyzer.resume_analyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResumeResponseDTO {
    private Long Id;
    private String fileName;
    private LocalDateTime uploadedAt;
}
