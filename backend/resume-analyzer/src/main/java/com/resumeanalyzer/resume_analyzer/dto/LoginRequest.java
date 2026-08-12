package com.resumeanalyzer.resume_analyzer.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequest {

    @NotBlank
    @Size(min = 5, max = 50)
    @Email
    private String email;
    @NotBlank
    @Size(min = 8, max = 13)
    private String password;
}
