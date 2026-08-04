package com.example.oauth2_basic_board.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name="member")
@Getter
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

    public static Member createUser(
            String userId,
            String password,
            String userName
    ) {
        return new Member(
                null,
                userId,
                password,
                userName,
                Role.ROLE_USER
        );
    }

    public static Member createUser(
            Long id,
            String userId,
            String userName,
            Role role
    ) {
        return new Member(
                id,
                userId,
                null,
                userName,
                role
        );
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
}
