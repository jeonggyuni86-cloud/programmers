package com.feignapi.dto;

import lombok.Builder;

@Builder
public record DataRequest(String name, int value) {
}
