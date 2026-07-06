package com.board_practice.dto;

import lombok.Builder;

@Builder
public record MemberJoinRequestDto(
    String userId,
    String password,
    String userName
){ }
