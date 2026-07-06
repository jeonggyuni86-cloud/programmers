package com.weather_feign.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record Items(
        List<Item> items
) {
}
