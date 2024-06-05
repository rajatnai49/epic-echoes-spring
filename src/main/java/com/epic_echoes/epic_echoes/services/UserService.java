package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.UserRequest;
import com.epic_echoes.epic_echoes.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse saveUser(UserRequest userRequest);
    UserResponse getUser();
    List<UserResponse> getAllUser();
}