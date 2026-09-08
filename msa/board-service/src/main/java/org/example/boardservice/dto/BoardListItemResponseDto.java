package org.example.boardservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BoardListItemResponseDto {

    private Long id;
    private String title;
    private String userId;
    private String userName;
    private Long commentCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime created;

    public static BoardListItemResponseDto from(BoardSearchRowDto row) {
        return new BoardListItemResponseDto(
                row.getId(),
                row.getTitle(),
                row.getUserId(),
                null,
                row.getCommentCount(),
                row.getCreated()
        );
    }
}
