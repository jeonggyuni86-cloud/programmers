package com.weather_feign.dto;

import lombok.Builder;

@Builder
public record Header(
        String resultCode,
        String resultMsg
) {
}
