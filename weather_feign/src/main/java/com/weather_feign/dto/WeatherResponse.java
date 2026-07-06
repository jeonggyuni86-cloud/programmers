package com.weather_feign.dto;

import lombok.Builder;

@Builder
public record WeatherResponse(
        Response response
) {
}
