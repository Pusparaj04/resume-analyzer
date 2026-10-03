package com.resumeanalyzer.resume_analyzer.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Schema;
import com.google.genai.types.Type;
import com.resumeanalyzer.resume_analyzer.dto.GeminiAnalysisResponseDTO;
import org.springframework.stereotype.Service;
import tools.jackson.databind.json.JsonMapper;

@Service
public class GeminiResumeAnalysisServiceImpl implements GeminiResumeAnalysisService{
    private static final String MODEL = "gemini-3.5-flash-lite";
    private final Client client;
    private final JsonMapper jsonMapper;

    public GeminiResumeAnalysisServiceImpl(Client client, JsonMapper jsonMapper) {
        this.client = client;
        this.jsonMapper = jsonMapper;
    }

    @Override
    public GeminiAnalysisResponseDTO analyzeResume(String resumeText, String jobDescription) {

        String prompt = """
                You are a professional resume analyzer.
                
                Analyze the candidate's resume against the provided job description.
                
                RESUME:
                %s
                
                JOB DESCRIPTION:
                %s
                
                ANALYSIS REQUIREMENTS:
                
                1. strengths:
                   Identify the areas where the candidate's resume aligns well with the
                   requirements of the job. Briefly explain how the candidate is fit for
                   given job profile.
                   
                   
                2. weaknesses:
                   Identify areas where the resume is weak, unclear, or does not sufficiently
                   demonstrate alignment with the job requirements. Shortly elaborate weak point
                   of candidate.
                
                3. missing skills:
                   Identify important technical or professional skills required or strongly 
                   indicated by the job description that are not sufficiently demonstrated in
                   the resume. Rather than just mentioning missing skill, express it in a simple
                   sentence.
                   
                4. suggestions:
                   Provide specific and actionable suggestions for improving the resume for this
                   particular job. 
                   
                RULES:
                
                    - Base your analysis ONLY on the information provided in the resume and job description.
                    - Do NOT invent skills, experience, projects, certifications, achievements, or qualifications.
                    - Consider a skill present only when there is evidence of it in the resume.
                    - Focus the analysis on the specific job description.
                    - Do not give generic resume advise unless it is relevant to this particular job.
                    - Keep each item concise and useful.
                    - Return only the requested JSON structure.     
                """.formatted(resumeText, jobDescription);

        GenerateContentConfig config =
                GenerateContentConfig.builder()
                        .responseMimeType("application/json")
                        .responseSchema(
                                Schema.builder()
                                        .type(Type.Known.OBJECT)
                                        .properties(
                                                java.util.Map.of(
                                                        "strengths",
                                                        Schema.builder()
                                                                .type(Type.Known.ARRAY)
                                                                .items(
                                                                        Schema.builder()
                                                                                .type(Type.Known.STRING)
                                                                                .build()
                                                                )
                                                                .build(),

                                                        "weaknesses",
                                                        Schema.builder()
                                                                .type(Type.Known.ARRAY)
                                                                .items(
                                                                        Schema.builder()
                                                                                .type(Type.Known.STRING)
                                                                                .build()
                                                                )
                                                                .build(),

                                                        "missingSkills",
                                                        Schema.builder()
                                                                .type(Type.Known.ARRAY)
                                                                .items(
                                                                        Schema.builder()
                                                                                .type(Type.Known.STRING)
                                                                                .build()
                                                                )
                                                                .build(),

                                                        "suggestions",
                                                        Schema.builder()
                                                                .type(Type.Known.ARRAY)
                                                                .items(
                                                                        Schema.builder()
                                                                                .type(Type.Known.STRING)
                                                                                .build()
                                                                )
                                                                .build()
                                                )
                                        )
                                        .required(
                                                "strengths",
                                                "weaknesses",
                                                "missingSkills",
                                                "suggestions"
                                        )
                                        .build()
                        )
                        .build();

        GenerateContentResponse response =
                client.models.generateContent(
                        MODEL,
                        prompt,
                        config
                );

        String jsonResponse = response.text();
        return parseResponse(jsonResponse);
    }

    private GeminiAnalysisResponseDTO parseResponse(String jsonResponse){

        try{
            return jsonMapper.readValue(
                    jsonResponse,
                    GeminiAnalysisResponseDTO.class
            );
        }catch (Exception e){
            throw new RuntimeException(
                    "Failed to parse gemini response.",
                    e
            );
        }
    }

}
