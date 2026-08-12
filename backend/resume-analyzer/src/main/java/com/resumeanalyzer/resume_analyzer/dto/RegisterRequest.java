package com.resumeanalyzer.resume_analyzer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    @NotNull
    private String name;
    @NotNull
    @Size(min = 5, max = 50)
    @Email
    private String email;
    @NotNull
    @Size(min = 8, max = 13)
    private String password;
}
