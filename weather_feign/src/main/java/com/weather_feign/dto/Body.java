package com.weather_feign.dto;

import lombok.Builder;

@Builder
public record Body(
        Items items,
        int pageNo,
        int numOfRows,
        int totalCount
) {
}
