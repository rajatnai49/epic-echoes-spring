package com.epic_echoes.epic_echoes.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_VOTE")
public class UserVote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;

    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    public enum VoteType {
        UPVOTE, DOWNVOTE
    }
}
