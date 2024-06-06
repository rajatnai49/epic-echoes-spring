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
public class UserRequest {

    private UUID id;
    private String username;
    private String password;
    private Set<UserRole> roles;

}