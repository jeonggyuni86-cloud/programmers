package com.example.oauth2_basic_board.domain.entity;


import com.example.oauth2_basic_board.config.oauth.AuthProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name="member")
@Getter
@Builder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String userId;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(nullable = false, length = 20)
    private String userName;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Builder.Default
    private AuthProvider provider = AuthProvider.LOCAL;

    @Column(name = "provider_id", length = 100)
    private String providerId;

    public static Member createUser(
            String userId,
            String password,
            String userName,
            AuthProvider provider
    ) {
        return Member.builder()
                .userId(userId)
                .password(password)
                .userName(userName)
                .provider(provider)
                .build();
    }

    public static Member createUser(
            Long id,
            String userId,
            String userName,
            Role role,
            AuthProvider provider
    ) {
        return Member.builder()
                .id(id)
                .userId(userId)
                .password(userName)
                .userName(userName)
                .role(role)
                .provider(provider)
                .build();
    }

    public void promoteToAdmin() {
        changeRole(Role.ROLE_ADMIN);
    }

    public void demoteToUser() {
        changeRole(Role.ROLE_USER);
    }

    private void changeRole(Role role) {
        if(this.role == role) {
            throw new IllegalStateException("Cannot change role as it is the same as the role");
        }
        this.role = role;
    }

    public Member updateProfile(String name) {
        this.userName = name;
        return this;
    }
}
