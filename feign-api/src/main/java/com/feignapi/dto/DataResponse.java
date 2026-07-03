package com.feignapi.dto;

import lombok.Builder;

@Builder
public record DataResponse(long id, String name, int value) {
}
