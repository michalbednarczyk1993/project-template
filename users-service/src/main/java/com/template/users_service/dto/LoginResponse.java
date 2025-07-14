package com.template.users_service.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String email;
    private String firstName;
    private String lastName;
    private boolean isAdmin;
}