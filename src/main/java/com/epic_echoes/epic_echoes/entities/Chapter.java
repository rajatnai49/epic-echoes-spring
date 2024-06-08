package com.epic_echoes.epic_echoes.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CHAPTER")
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @Column(nullable = false)
    private String title;

    private Long upVotes = 0L;
    private Long downVotes = 0L;

    @Column(nullable = false)
    private Integer chapterNumber;

    @Lob
    @Column(name = "CHAPTER_CONTENT", columnDefinition = "TEXT")
    private String chapterContent;

    @ManyToOne
    @JoinColumn(name = "storybook_id", nullable = false)
    private Storybook storybook;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;
}
