package org.example.boardservice.config.security;

public record AuthenticatedUser(
        Long id,
        String userId,
        String userName,
        String role
) {

    public boolean isAdmin() {
        return "ROLE_ADMIN".equals(role);
    }
}
