package com.project.ticket_service.dto.request;

import com.project.ticket_service.model.enums.Role;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private Role role;
}
