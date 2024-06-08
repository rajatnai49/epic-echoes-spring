package com.epic_echoes.epic_echoes.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "STORYBOOK")
public class Storybook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    private String name;

    @Lob
    @Column(name = "PLOT", columnDefinition = "TEXT")
    private String plot;

    private Long maxChapterLength;
    private Long minChapterLength;

    @OneToMany(mappedBy = "storybook", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("chapterNumber ASC")
    private List<Chapter> chapters;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;

    @Enumerated(EnumType.STRING)
    private Privacy privacy;

    @OneToMany(mappedBy = "storybook", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StorybookUserPermission> permissions;

    @ManyToMany
    @JoinTable(
            name = "storybook_genre",
            joinColumns = @JoinColumn(name = "storybook_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    public enum Privacy {
        EVERYONE_VIEW, // Everyone can see
        PERMITTED_USERS_VIEW, // Only permitted users can see
        EVERYONE_EDIT, // Everyone can see and edit
        PERMITTED_USERS_EDIT // Everyone can see, but only permitted users can edit
    }
}
