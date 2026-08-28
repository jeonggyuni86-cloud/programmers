package org.example.authservice.dto;

import lombok.Data;
import lombok.ToString;
import org.example.authservice.domain.entity.Role;
import org.example.authservice.domain.entity.User;

@Data
@ToString
public class SignUpRequestDto {
    private String userId;
    private String password;
    private String userName;
    private Role role;

    public User touser(String encodedPassword) {
        return User.builder()
                .userId(userId)
                .password(encodedPassword)
                .name(userName)
                .role(role != null ? role : Role.ROLE_USER)
                .build();
    }
}
