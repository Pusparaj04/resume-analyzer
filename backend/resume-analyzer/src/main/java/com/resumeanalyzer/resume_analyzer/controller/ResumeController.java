package com.resumeanalyzer.resume_analyzer.controller;


import com.resumeanalyzer.resume_analyzer.dto.JobDescriptionRequestDTO;
import com.resumeanalyzer.resume_analyzer.service.AiAnalysisService;
import com.resumeanalyzer.resume_analyzer.service.PdfParserService;
import com.resumeanalyzer.resume_analyzer.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {
    @Autowired
    ResumeService resumeService;
    @Autowired
    AiAnalysisService analysisService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadResume(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(resumeService.handleResumeUpload(file));
    }

    @PostMapping("/match")
    public ResponseEntity<?> matchResumeWithJob(@RequestPart("file") MultipartFile file,
                                                @RequestPart("jobDescription") String jobDescription) throws IOException {
        return ResponseEntity.ok(
                resumeService.matchResumeWithJD(file, jobDescription)
        );
    }
}
