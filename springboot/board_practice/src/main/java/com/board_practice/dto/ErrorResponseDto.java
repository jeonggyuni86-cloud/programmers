package com.board_practice.dto;

import lombok.Builder;

@Builder
public record ErrorResponseDto(int status, String message) { }
