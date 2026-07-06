package com.board_practice.dto;

import lombok.Builder;

@Builder
public record LoginRequestDto(String username, String password) { }
