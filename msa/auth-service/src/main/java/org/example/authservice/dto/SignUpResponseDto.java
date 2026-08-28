package org.example.authservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class SignUpResponseDto {
    private String url;
}
