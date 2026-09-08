package org.example.boardservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BoardSearchRowDto {

    private Long id;
    private String title;
    private String userId;
    private Long commentCount;
    private LocalDateTime created;
}
