package com.resumeanalyzer.resume_analyzer.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ResumeServiceImpl implements ResumeService{


    private  PdfParserService pdfParserService;

    public ResumeServiceImpl(PdfParserService pdfParserService) {
        this.pdfParserService = pdfParserService;
    }

    @Override
    public String handleResumeUpload(MultipartFile file) throws IOException {
        if(file.isEmpty()){
            throw new RuntimeException("File is Empty!!");
        }
        String extractedText = pdfParserService.extractText(file);
        return extractedText;
    }
}
