package com.example.oauth2.dto;

import com.example.oauth2.domain.entity.entitiy.Role;
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
