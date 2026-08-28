package org.example.webservice.dto;

import org.example.webservice.enums.Role;

public record SignUpRequestDto(
        String userId,
        String password,
        String userName,
        Role role
) {
}
