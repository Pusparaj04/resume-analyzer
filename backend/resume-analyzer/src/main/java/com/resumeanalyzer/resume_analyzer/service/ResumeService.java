package com.resumeanalyzer.resume_analyzer.service;

import com.resumeanalyzer.resume_analyzer.dto.AnalysisResponseDTO;
import com.resumeanalyzer.resume_analyzer.dto.JobMatchResponseDTO;
import com.resumeanalyzer.resume_analyzer.dto.ResumeResponseDTO;
import com.resumeanalyzer.resume_analyzer.model.Resume;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ResumeService {
    AnalysisResponseDTO handleResumeUpload(MultipartFile file) throws IOException;
    String extractResumeText(MultipartFile file) throws IOException;

    JobMatchResponseDTO matchResumeWithJD(MultipartFile file, String jobDescription) throws IOException;

    List<ResumeResponseDTO> getResume();
}
