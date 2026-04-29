package com.resumeanalyzer.resume_analyzer.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ResumeService {
    String handleResumeUpload(MultipartFile file) throws IOException;
}
