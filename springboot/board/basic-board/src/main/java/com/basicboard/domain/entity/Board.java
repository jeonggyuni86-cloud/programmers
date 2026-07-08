package com.basicboard.domain.entity;


import com.basicboard.constant.DateTimeFormatConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern= DateTimeFormatConstant.DATETIME_PATTERN)
    private LocalDateTime created;

    // * 게시글 수정
    // Setter 대신 메서드 둔 이유  + JPA 변경 감지
    // 이 엔티티는 Getter만 있고 Setter는 없다. (아무데서나 수정 방지)
    // - Setter가 있을 경우 언제 어디서 바뀌는지 추적이 어려워진다.
    // - 대신 "게시글을 수정한다" 라는 의도가 드러나는 메서드로 모은다" (도메인 주도 스타일)
    // * JPA 변경 감지(dirty checking)
    // - 트랜젝션 안에서 조회한 엔티티(영속 상태)의 필드를 바꾸면,
    // 트랜젝션이 끝날 떼 JPA가 "바뀐 것"을 감지해, UPDATE SQL을 자동으로 수행한다.
    // - 그래서 따로 boardRepository에서 호출을 날리지 않아도 된다.
    public void update(String title, String content, String filePath) {
        this.title = title;
        this.content = content;
        this.filePath = filePath;
    }
}
