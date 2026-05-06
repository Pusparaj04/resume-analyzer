package com.resumeanalyzer.resume_analyzer.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PdfParserServiceImpl implements PdfParserService{
    @Override
    public String extractText(MultipartFile file) throws IOException {
        try(PDDocument document = PDDocument.load(file.getInputStream())){
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            return  pdfTextStripper.getText(document);
        }catch (IOException e){
            throw new RuntimeException("Error while reading PDF file", e);
        }
    }
}
