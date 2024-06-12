package com.epic_echoes.epic_echoes.dto;

import com.epic_echoes.epic_echoes.entities.UserRole;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserRequest {

    private UUID id;
    private String username;
    private String password;
    private Set<UserRole> roles;

}