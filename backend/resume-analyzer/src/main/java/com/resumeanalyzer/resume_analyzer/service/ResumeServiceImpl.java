package com.resumeanalyzer.resume_analyzer.service;

import com.resumeanalyzer.resume_analyzer.model.Resume;
import com.resumeanalyzer.resume_analyzer.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ResumeServiceImpl implements ResumeService{

    @Autowired
    private  PdfParserService pdfParserService;
    @Autowired
    private ResumeRepository resumeRepository;
    @Autowired
    private AiAnalysisService aiAnalysisService;

    @Override
    public Map<String, Object> handleResumeUpload(MultipartFile file) throws IOException {
        if(file.isEmpty()){
            throw new RuntimeException("File is Empty!!");
        }
        String extractedText = pdfParserService.extractText(file);

        Map<String, Object> analysis = aiAnalysisService.resumeAnalyzer(extractedText);

        Resume resume = new Resume();
        resume.setFileName(file.getOriginalFilename());
        resume.setUploadedAt(LocalDateTime.now());
        resume.setContent(extractedText);

        resumeRepository.save(resume);
        return analysis;
    }
}
