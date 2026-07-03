package com.feignclient.dto;

import lombok.Builder;

// * DTO (Data Transfer Object): 데이터 전송용 객체
// 계층간 또는 서버 간에 데이터를 주고 받을 때 사용
// (POST 등으로 요청 본문(Body)에 데이터를 실어 보낼때 활용한다)

@Builder
public record DataRequest(String name, int value) {
}
