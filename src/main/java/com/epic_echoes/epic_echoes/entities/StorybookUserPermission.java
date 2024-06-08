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
@Table(name = "STORYBOOK_USER_PERMISSION")
public class StorybookUserPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "storybook_id", nullable = false)
    private Storybook storybook;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    public enum RoleType {
        WRITER,     // Can do anything
        CO_WRITER,  // Can do anything except delete
        MANAGER,    // Can view, add, and edit
        VIEWER      // Can only view
    }
}
