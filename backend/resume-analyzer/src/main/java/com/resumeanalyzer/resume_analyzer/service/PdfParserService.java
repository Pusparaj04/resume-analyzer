package com.resumeanalyzer.resume_analyzer.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PdfParserService {
    String extractText(MultipartFile file) throws IOException;
}
