package com.basicboard.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

//@Aspect
// - 이 클래스는 공통기능(횡단 관심사)를 모아둔 Aspect
// - 이 어노테이션이 붙어야 스프링 AOP가 이 클래스 안의 포인트컷/ 어드바이스를 인식한다
// - "AOP" 규칙을 담고 있다는 표시일 뿐, 스프링이 관리하는 빈으로 등록해주지 않는다.
// - 따라서 Component를 직접 붙여준다
// - 스프링 컨테이너에 빈으로 등록해야, 스프링이 이 Aspect를 찾아서 실제로 동작한다.

@Aspect
@Slf4j
// * System.out.println -> @slf4j 로 교체한 이유
// 1) 레벨이 없다: 전부 같은 급이라, 운영에서 "디버그성 출력만 끄기" 같은 제어가 불가능
// 2) 맥락이 없다: 시간/스레드/클래스 이름이 자동으로 안붙는다. (문제 추적이 힘들다)
// 3) 목적지가 고정이다: 콘솔에만 나간다. 파일 저장, 날짜별 분할(롤링) 같은 걸 못한다.
// 4) 성능에 불리하다: 동기 출력이라 요청이 몰리면 병목이 될 수 있다.
// # log.info(...) 로 바꾸면 위 네 가지가 전부 해결된다 - 출력 형태를 보면 차이가 바로 보인다:
//     System.out : [요청 시작] GET /api/boards -> ...
//     log.info   : 2026-07-14T10:00:00.123+09:00  INFO 12345 --- [nio-8080-exec-1] c.e.s.b.aop.LoggingAspect : [요청 시작] ...
//                  └ 시간 ──────────────────────┘ └레벨┘ └PID┘  └── 스레드 ──────┘ └── 어느 클래스가 찍었나 ┘
@Component
public class LoggingAspect {

    // * Pointcut : 어디에 적용할지 정의한다
    // execution : 메서드 "실행" 지점을 대상으로 한다는 지시어
    // - * : (맨앞) 반환타입에 대해 설명 (무엇이든 상관 없다)
    // - com.basicboard.controller..: Controller 패키지와 그 하위 패키지 전부
    // - * : 그안의 모든 클래스와 모든 메서드
    // - (..) : 메서드 파라미터는 개수/타입 상관없이 모두

    @Pointcut("execution(* com.basicboard.controller..*(..))")
    public void controllerLog() {
        // 메서드 본문(Body)는 비워둔다.
        // 실제 로직이 아닌 "대상을 가르키는 이름표" 역할만 한다
    }

    // @Around  "언제/ 무엇을" 할지 정의하는 어드바이스
    // 어드바이스에슨 5가지 종류가 있다.
    // @Before          : 대상 메서드 실행 "직전"에만 실행
    // @AftrerReturning : 대상 메서드가 "정상 반환된 후" 실행
    // @AfterThrowning  : 대상 메서드가 "예외를 던졌을 때" 실행
    // @After           : 정상 / 예외 상관없이 "끝나면 항상" 실행
    // @Around          : 대상 메서드 실행을 "통째로 감싼다". 전/후/예외를 한 메서드에서 제어한다.
    @Around("controllerLog()")
    public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        // * ProceedingJoinPoint
        // - 지금 가로챈 그 지점(메서드 호출)에 대한 정보를 담은 객체이다.
        // - 어떤 메서드가 호출 됐는지, 넘어온 인자는 무엇인지 등을 꺼낼 수 있다.

        String method = joinPoint.getSignature().getDeclaringType() + "." + joinPoint.getSignature().getName();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String httpInfo = " ";
        if(attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            httpInfo = request.getMethod() + "." + request.getRequestURL();
        }
        // == 대상 메서드 실행 "전 로직 ==
        log.info("[요청 시작] {} -> {}", httpInfo, method);
        log.info("[파라미터] {}", Arrays.toString(joinPoint.getArgs()));

        long start = System.nanoTime();
        try {
            //이 한줄을 기준으로 요청받은 메서드 실행 전 실행후로 나뉜다.
            Object result = joinPoint.proceed(); // 대상 메서드 실행
            // => 대상 메서드가 정상 종료 된 후 로깅

            long during = System.nanoTime() - start;
            log.info("[요청 완료] {} : {}ms", method, String.format("%.3f ms", during/ 1_000_000.0));
            return result;
        } catch(Throwable e) {
            long during = System.nanoTime() - start;
            log.warn("[요청 실패] {} : {}ms, 예외 메시지 : {} ",method, String.format("%.3f ms", during/ 1_000_000.0), e.getMessage());

            // - 예외를 삼켜버리면 컨트롤러는 정상 처리된 것처럼 보여 버그가 된다.
            throw e;
        }
    }
}
