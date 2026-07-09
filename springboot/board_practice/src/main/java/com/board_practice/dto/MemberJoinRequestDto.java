package com.board_practice.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinRequestDto{
    private String userId;
    private String password;
    private String userName;
}
