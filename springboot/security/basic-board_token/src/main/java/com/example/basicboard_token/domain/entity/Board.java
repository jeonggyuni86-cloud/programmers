package com.example.basicboard_token.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @Column(length = 255)
    private String filePath;

    @Column(nullable = false, length = 200)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    public void update(String title, String content, String filePath) {
        this.title = title;
        this.content = content;
        this.filePath = filePath;
    }

    public static Board from(
            String title,
            String content,
            String userId,
            String filePath,
            List<Comment> comments
    ) {
        return new Board(
                null,
                title,
                content,
                userId,
                filePath,
                LocalDateTime.now(),
                comments
        );
    }
    public static Board from(
            String title,
            String content,
            String userId,
            String filePath
    ) {
        return new Board(
                null,
                title,
                content,
                userId,
                filePath,
                LocalDateTime.now(),
                new ArrayList<>()
        );
    }
}
