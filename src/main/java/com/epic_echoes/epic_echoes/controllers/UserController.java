package com.epic_echoes.epic_echoes.controllers;

import com.epic_echoes.epic_echoes.dto.*;
import com.epic_echoes.epic_echoes.entities.RefreshToken;
import com.epic_echoes.epic_echoes.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final StorybookUserPermissionService permissionService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService, RefreshTokenService refreshTokenService, StorybookUserPermissionService permissionService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.permissionService = permissionService;
    }

    @PostMapping(value = "/save")
    public ResponseEntity saveUser(@RequestBody UserRequest userRequest) {
        try {
            UserResponse userResponse = userService.saveUser(userRequest);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/users")
    public ResponseEntity getAllUsers() {
        try {
            List<UserResponse> userResponses = userService.getAllUser();
            return ResponseEntity.ok(userResponses);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/users/filterByPrivacy")
    public ResponseEntity<List<UserResponse>> filterUsersBasedOnPrivacy(
            @RequestParam UUID storybookId,
            @RequestParam(required = false) String privacy) {
        List<UserResponse> users = permissionService.filterUsersBasedOnPrivacy(storybookId, privacy);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUserProfile() {
        try {
            UserResponse userResponse = userService.getUser();
            return ResponseEntity.ok().body(userResponse);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/test")
    public String test() {
        try {
            return "Welcome";
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        System.out.println("Authenticating user: " + authRequestDTO.getUsername());
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
            System.out.println("Authentication object: " + authentication);
        } catch (Exception e) {
            System.out.println("Authentication failed for user: " + authRequestDTO.getUsername());
            e.printStackTrace();
            throw new UsernameNotFoundException("Invalid user request..!!");
        }

        if (authentication.isAuthenticated()) {
            System.out.println("User authenticated: " + authRequestDTO.getUsername());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            System.out.println("Generated refresh token: " + refreshToken.getToken());

            return JwtResponseDTO.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()))
                    .token(refreshToken.getToken()).build();
        } else {
            System.out.println("Invalid user request: " + authRequestDTO.getUsername());
            throw new UsernameNotFoundException("Invalid user request..!!");
        }
    }

    @PostMapping("/refreshToken")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        String token = refreshTokenRequestDTO.getToken().trim();
        System.out.println("Token Received: " + token);

        return refreshTokenService.findByToken(token)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserInfo)
                .map(userInfo -> {
                    String accessToken = jwtService.GenerateToken(userInfo.getUsername());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .token(token).build();
                }).orElseThrow(() -> new RuntimeException("Refresh Token is not in DB..!!"));
    }
}