package com.resumeanalyzer.resume_analyzer.service;

import com.resumeanalyzer.resume_analyzer.dto.AuthResponse;
import com.resumeanalyzer.resume_analyzer.dto.LoginRequest;
import com.resumeanalyzer.resume_analyzer.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest registerRequest);
    AuthResponse login(LoginRequest loginRequest);
}
