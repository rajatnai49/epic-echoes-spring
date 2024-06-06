package com.epic_echoes.epic_echoes.dto;

import com.epic_echoes.epic_echoes.entities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {

    private UUID id;
    private String username;
    private Set<UserRole> roles;


}