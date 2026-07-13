package com.example.board_refactor.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

@Entity
@Table(name = "board")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 50)
    private String userId;

    @Column
    private String filePath;

    @Column
    private LocalDateTime created;

    public void update(String title, String content, String filePath) {
        this.title = title;
        this.content = content;
        this.filePath = filePath;
    }
}
