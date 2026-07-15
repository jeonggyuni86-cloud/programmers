package com.basicboard.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// * @Scheduled - 정해진 시각/주기에 메서드를 자동 실행하는 배치 작업
// # 전제 조건 2가지
// - 메인 클래스에 @EnableScheduling 이 있어야 한다.
// - 이 클래스가 스프링 빈 이어야 한다(@Component)

// @Scheduled 메서드의 형태 : 파라미터 없음 + 반환 void
// - 정해진 시각에 "스프링이" 호출하는 것이라, 인자를 넘겨줄, 반환을 받을 주체도 없다.

// # 실행 시점을 지정하는 3가지 방식
// - cron : "달력 기준" 특정 시각(매일 09:00, 매주 월요일)
// - fixedDelay : "이전 실행이 끝난 뒤" ms 쉬고 다시 실행
// - fixedRate : "이전 실행이 시작된 뒤" ms 마다 실행
//     * fixedDelay vs fixedRate: 작업이 오래 걸릴 때 차이가 난다
//       예) 작업 3분 + 간격 5분 → fixedDelay 는 "끝나고 5분 뒤"(8분 주기), fixedRate 는 "시작 후 5분마다"(5분 주기)
//       겹치면 안 되는 작업(정리/집계)은 fixedDelay 가 안전하다

// # 주의: 기본 스케줄러는 "스레드 1개"다
//   - 모든 @Scheduled 작업이 한 줄로 서서 순서대로 돈다 → 한 작업이 오래 걸리면 다른 작업이 밀린다
//   - 작업이 많아지면 스레드 풀 설정(spring.task.scheduling.pool.size)으로 늘릴 수 있다

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardBatchScheduler {

    // (1) 일일 현황 리포트 - cron 방식
    // cron 표현식 읽는 법 - "0 0 9 * * *" = 매일 09:00:00
    //
    //     "0   0   9   *   *   *"
    //      │   │   │   │   │   └ 요일 (0~7, 0과 7은 일요일. * = 모든 요일)
    //      │   │   │   │   └──── 월   (1~12)
    //      │   │   │   └──────── 일   (1~31)
    //      │   │   └──────────── 시   (0~23) → 9시
    //      │   └──────────────── 분   (0~59) → 0분
    //      └──────────────────── 초   (0~59) → 0초
    // - 스프링 cron 은 "6자리"다. (맨 앞에 '초'가 있다.)
    // ex) 리눅스 crontab은 5자리(분 부터), crontab예제를 그대로 복사하면 한칸씩 밀려 이동한 시간에 돈다
    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul")
    public void dailyReport() {
        log.info("[일일 리포트 배치 시작]");


    }
}
