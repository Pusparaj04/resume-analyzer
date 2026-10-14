package com.resumeanalyzer.resume_analyzer.service;

import com.resumeanalyzer.resume_analyzer.exception.InvalidFileException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class PdfParserServiceImpl implements PdfParserService{
    @Override
    public String extractText(MultipartFile file) throws IOException {

        if(file == null || file.isEmpty()){
            throw new InvalidFileException("Resume file can't be empty!");
        }

        String fileName = file.getOriginalFilename();
        if(fileName == null || !fileName.toLowerCase().endsWith(".pdf")){
            throw new InvalidFileException("Only PDF files are supported for this operation!");
        }

        try(PDDocument document = PDDocument.load(file.getInputStream())){
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            String extractedText = pdfTextStripper.getText(document);
            if(extractedText == null || extractedText.isBlank()){
                throw new InvalidFileException("Resume file doesn't contain any readable text!");
            }
            return  extractedText;
        }catch (InvalidFileException e){
            throw e;
        }catch(IOException e){
            throw new InvalidFileException("Invalid or corrupted PDF file!");
        }
    }
}
