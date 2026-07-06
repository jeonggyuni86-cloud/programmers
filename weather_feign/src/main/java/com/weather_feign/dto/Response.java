package com.weather_feign.dto;

import lombok.Builder;

@Builder
public record Response(
        Header header,
        Body body
) {
}
