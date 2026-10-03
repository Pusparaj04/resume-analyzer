package com.resumeanalyzer.resume_analyzer.config;

import com.google.genai.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Cipher;

@Configuration
public class GeminiConfig {
    @Bean
    public Client geminiClient(){
        return new Client();
    }
}
