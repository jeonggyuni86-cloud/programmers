package com.example.board_refactor.domain.entity;

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
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED) // JPA는 기본 생성자가 필요하지만, 외부에서 무분별한 생성을 막는다.
public class Member {
    @Id
    @GeneratedValue(strategy = IDENTITY) // auto increment
    private Long id;

    @Column(nullable = false, length = 50)
    private String userId;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(nullable = false, length = 20)
    private String userName;
}