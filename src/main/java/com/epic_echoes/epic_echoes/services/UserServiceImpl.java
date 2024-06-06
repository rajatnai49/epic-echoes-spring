package com.epic_echoes.epic_echoes.services;

import com.epic_echoes.epic_echoes.dto.UserRequest;
import com.epic_echoes.epic_echoes.dto.UserResponse;
import com.epic_echoes.epic_echoes.entities.UserInfo;
import com.epic_echoes.epic_echoes.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponse saveUser(UserRequest userRequest) {
        if(userRequest.getUsername() == null){
            throw new RuntimeException("Parameter username is not found in request..!!");
        } else if(userRequest.getPassword() == null){
            throw new RuntimeException("Parameter password is not found in request..!!");
        }

        UserInfo savedUser = null;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = userRequest.getPassword();
        String encodedPassword = encoder.encode(rawPassword);

        UserInfo user = modelMapper.map(userRequest, UserInfo.class);
        user.setPassword(encodedPassword);
        if(userRequest.getId() != null){
            UserInfo oldUser = userRepository.findFirstById(userRequest.getId());
            if(oldUser != null){
                oldUser.setId(user.getId());
                oldUser.setPassword(user.getPassword());
                oldUser.setUsername(user.getUsername());
                oldUser.setRoles(user.getRoles());

                savedUser = userRepository.save(oldUser);
                userRepository.refresh(savedUser);
            } else {
                throw new RuntimeException("Can't find record with identifier: " + userRequest.getId());
            }
        } else {
            savedUser = userRepository.save(user);
        }
        userRepository.refresh(savedUser);
        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        return userResponse;
    }

    @Override
    public UserResponse getUser() {
        return null;
    }

    @Override
    public List<UserResponse> getAllUser() {
        return List.of();
    }
}
