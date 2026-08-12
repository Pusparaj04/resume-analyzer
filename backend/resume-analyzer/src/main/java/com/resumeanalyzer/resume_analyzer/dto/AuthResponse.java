package com.resumeanalyzer.resume_analyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuthResponse {
    private String token;
    private String email;
    private String message;
}
