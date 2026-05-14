package com.resumeanalyzer.resume_analyzer.service;

import com.resumeanalyzer.resume_analyzer.dto.AnalysisResponseDTO;
import com.resumeanalyzer.resume_analyzer.dto.JobMatchResponseDTO;
import com.resumeanalyzer.resume_analyzer.model.JobMatchAnalysis;
import com.resumeanalyzer.resume_analyzer.model.Resume;
import com.resumeanalyzer.resume_analyzer.model.ResumeAnalysis;
import com.resumeanalyzer.resume_analyzer.repository.JobMatchAnalysisRepository;
import com.resumeanalyzer.resume_analyzer.repository.ResumeAnalysisRepository;
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
    @Autowired
    private ResumeAnalysisRepository analysisRepository;
    @Autowired
    private JobMatchAnalysisRepository jobMatchAnalysisRepository;

    @Override
    public AnalysisResponseDTO handleResumeUpload(MultipartFile file) throws IOException {
        if(file.isEmpty()){
            throw new RuntimeException("File is Empty!!");
        }
//        extract text
        String extractedText = pdfParserService.extractText(file);

//        Analyze
        AnalysisResponseDTO analysis = aiAnalysisService.resumeAnalyzer(extractedText);

//        save resume
        Resume resume = new Resume();
        resume.setFileName(file.getOriginalFilename());
        resume.setUploadedAt(LocalDateTime.now());
        resume.setContent(extractedText);

        Resume savedResume = resumeRepository.save(resume);

//        save analysis
        ResumeAnalysis resumeAnalysis = new ResumeAnalysis();
        resumeAnalysis.setScore(analysis.getScore());
        resumeAnalysis.setFoundSkills(
                String.join(",", analysis.getFoundSkills()));
        resumeAnalysis.setMissingSkills(
                String.join(",", analysis.getMissingSkills()));
        resumeAnalysis.setResume(savedResume);
        savedResume.setAnalysis(resumeAnalysis);
        analysisRepository.save(resumeAnalysis);

        return analysis;
    }

    @Override
    public String extractResumeText(MultipartFile file) throws IOException {
        if(file.isEmpty()){
            throw new RuntimeException("File is Empty!!");
        }

        return pdfParserService.extractText(file);
    }

    @Override
    public JobMatchResponseDTO matchResumeWithJD(MultipartFile file, String jobDescription) throws IOException {
        String extractedText = extractResumeText(file);
        JobMatchResponseDTO responseDTO = aiAnalysisService.matchJobDescription(extractedText, jobDescription);

        Resume resume = new Resume();
        resume.setFileName(resume.getFileName());
        resume.setContent(extractedText);
        resume.setUploadedAt(LocalDateTime.now());

        Resume savedResume = resumeRepository.save(resume);

        JobMatchAnalysis jobMatchAnalysis = new JobMatchAnalysis();
        jobMatchAnalysis.setJobDescription(jobDescription);
        jobMatchAnalysis.setMatchedKeywords(String.join(",", responseDTO.getMatchedKeywords()));
        jobMatchAnalysis.setMissingKeywords(String.join(",", responseDTO.getMissingKeywords()));
        jobMatchAnalysis.setMatchPercentage(responseDTO.getMatchPercentage());
        jobMatchAnalysis.setAnalyzedAt(LocalDateTime.now());
        jobMatchAnalysis.setResume(savedResume);
        jobMatchAnalysisRepository.save(jobMatchAnalysis);

        return responseDTO;
    }
}
