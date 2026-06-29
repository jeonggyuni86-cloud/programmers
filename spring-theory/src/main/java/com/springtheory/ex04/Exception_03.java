package com.springtheory.ex04;

// * 예외 처리 전략 3가지
// '모든 예외는 복구되던지, 아니면 분명히 통보되어야 한다'는 원칙을 실제로 지키는 방법은
// 1) 예외 복구 2) 예외 회피 3) 예외 전환

import java.sql.SQLException;

public class Exception_03 {

    // 1) 예외 복구
    // - 예외 상황을 파악하고, 문제를 해결해서 '정상 흐름으로 되돌리는' 것.
    // - 예외가 났어도 사용자/프로그램 입장에선 아무 문제 없이 작업이 끝난 것처럼 만든다.
    // - 대표 예: 재시도(retry), 대체값/대체경로(fallback).
    //   주의) 단순히 catch로 잡고 무시하는 '예외 블랙홀'은 복구가 아니다. 정상 상태로 되돌려야 복구다.
    String 예외복구_재시도() {
        int maxRetry = 3;
        for (int attempt = 0; attempt < maxRetry; attempt++) {

            try {
                return fetchFromNetwork();
            } catch (SQLException e) {
                // 일시적 오류일 수 있으니, 정해진 횟수만큼 다시 시도한다.
                System.out.println(attempt + 1 + "/" + maxRetry + "번째 시도 실패, 재시도 합니다");
            }
        }

        // 정해진 횟수를 모두 실패했다면, 더는 무시하지 말고 분명히 통보(중단)한다.
        throw new RuntimeException("재시도 " + maxRetry + "회 모두 실패했습니다.");
    }

    // ========== 설명 보조 코드 ==========
    String fetchFromNetwork() throws SQLException {
        throw new SQLException("일시적 연결 오류");
    }

    static void main(String[] args) {

    }
}
