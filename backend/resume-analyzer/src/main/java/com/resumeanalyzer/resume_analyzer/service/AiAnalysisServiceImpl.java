package com.resumeanalyzer.resume_analyzer.service;

import com.resumeanalyzer.resume_analyzer.dto.AnalysisResponseDTO;
import com.resumeanalyzer.resume_analyzer.dto.JobMatchResponseDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AiAnalysisServiceImpl implements AiAnalysisService{
    @Override
    public AnalysisResponseDTO resumeAnalyzer(String text) {
        String lowerText = text.toLowerCase();

        List<String> foundSkills = new ArrayList<>();
        List<String> missingSkills = new ArrayList<>();

        for(String skill : SKILLS){
            if(lowerText.contains(skill)){
                foundSkills.add(skill);
            }else{
                missingSkills.add(skill);
            }
        }

        float score = (float) (foundSkills.size() * 100) / SKILLS.size();

        score = Math.round(score * 100f) / 100f;


        return new AnalysisResponseDTO(
                score,
                foundSkills,
                missingSkills
        );
    }

    @Override
    public JobMatchResponseDTO matchJobDescription(String resumeText, String jobDescription) {
        String resume = resumeText.toLowerCase();
        String jd = jobDescription.toLowerCase();
        List<String> matchedKeywords = new ArrayList<>();
        List<String> missingKeywords = new ArrayList<>();
        List<String> stopWords = Arrays.asList(
                "looking",
                "for",
                "with",
                "and",
                "the",
                "developer",
                "experience",
                "knowledge",
                "skills"
        );
        jd = jd.replaceAll("[^a-zA-Z0-9 ]", "");
        String[] keywords = jd.split(" ");


        int totalKeywords = 0;
        int matchedCount = 0;

        for(String keyword : keywords){
            keyword = keyword.trim();
            if(keyword.length() < 3){
                continue;
            }
            if(stopWords.contains(keyword)){
                continue;
            }
            totalKeywords++;

            if(resume.contains(keyword)){
                matchedKeywords.add(keyword);
                matchedCount++;
            }else{
                missingKeywords.add(keyword);
            }
        }

        float matchPercentage = 0;
        if(totalKeywords > 0){
            matchPercentage = (float) (matchedCount * 100) / totalKeywords;
        }
        return new JobMatchResponseDTO(
                matchPercentage,
                matchedKeywords,
                missingKeywords
        );
    }
}
