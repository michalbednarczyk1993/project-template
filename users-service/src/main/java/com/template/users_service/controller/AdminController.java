package com.template.users_service.controller;

import com.template.users_service.dto.UserDto;
import com.template.users_service.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/addAdminRights")
    public ResponseEntity<String> addAdminRights(@RequestParam String email, Authentication authentication) {
        String currentEmail = authentication.getName();
        if (currentEmail.equals(email)) {
            return ResponseEntity.badRequest().body("Nie można nadać sobie uprawnień admina (już je masz)");
        }
        boolean result = adminService.setAdminRights(email, true);
        return result ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/removeAdminPrivileges")
    public ResponseEntity<String> removeAdminPrivileges(@RequestParam String email, Authentication authentication) {
        String currentEmail = authentication.getName();
        if (currentEmail.equals(email)) {
            return ResponseEntity.badRequest().body("Nie można odebrać sobie uprawnień admina");
        }
        boolean result = adminService.setAdminRights(email, false);
        return result ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) {
        return adminService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable UUID id, @Valid @RequestBody UserDto userDto) {
        if (adminService.getUserById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userDto.setId(id);
        return ResponseEntity.ok(adminService.saveUser(userDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        if (adminService.getUserById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
