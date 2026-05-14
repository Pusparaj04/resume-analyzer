package com.resumeanalyzer.resume_analyzer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class JobMatchAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "Text")
    private String jobDescription;

    @Column(columnDefinition = "Text")
    private String matchedKeywords;

    @Column(columnDefinition = "Text")
    private String missingKeywords;

    private float matchPercentage;
    private LocalDateTime analyzedAt;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
