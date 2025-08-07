package com.template.users_service.controller;

import com.template.users_service.dto.UserDto;
import com.template.users_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/aboutMe")
    public ResponseEntity<UserDto> getAboutMe(Authentication authentication) {
        String email = authentication.getName();
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/aboutMe")
    public ResponseEntity<UserDto> updateAboutMe(Authentication authentication, @RequestBody UserDto userDto) {
        String email = authentication.getName();
        return userService.updateUserByEmail(email, userDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
