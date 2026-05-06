package com.resumeanalyzer.resume_analyzer.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ResumeService {
    Map<String, Object> handleResumeUpload(MultipartFile file) throws IOException;
}
