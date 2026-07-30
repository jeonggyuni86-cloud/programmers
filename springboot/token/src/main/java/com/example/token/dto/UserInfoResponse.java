package com.example.token.dto;

import com.example.token.domain.entitiy.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponse {

    private long id;
    private String userId;
    private String userName;
    private Role role;

}
