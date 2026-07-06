package com.weather_feign.dto;

import lombok.Builder;

@Builder
public record Item(
        String baseDate,
        String baseTime,
        String category,
        int nx,
        int ny,
        String obsrValue
) {
}
