package com.resumeanalyzer.resume_analyzer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResumeAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float score;
    @Column(columnDefinition = "Text")
    private String foundSkills;
    @Column(columnDefinition = "Text")
    private String missingSkills;

    @OneToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
