package com.resumeanalyzer.resume_analyzer.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AiAnalysisServiceImpl implements AiAnalysisService{
    @Override
    public Map<String, Object> resumeAnalyzer(String text) {
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

        Map<String, Object> result  = new HashMap<>();
        result.put("Found Skills", foundSkills);
        result.put("Missing Skills", missingSkills);
        result.put("Score", score);
        return result;
    }
}
