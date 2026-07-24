package com.resumeanalyzer.resume_analyzer.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank
    @Size(min = 5, max = 50)
    @Email
    private String email;
    @NotBlank
    @Size(min = 8, max = 13)
    private String password;
}
