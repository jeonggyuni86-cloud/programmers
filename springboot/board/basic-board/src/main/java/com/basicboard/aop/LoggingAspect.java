package com.basicboard.aop;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//@Aspect
// - 이 클래스는 공통기능(횡단 관심사)를 모아둔 Aspect
// - 이 어노테이션이 붙어야 스프링 AOP가 이 클래스 안의 포인트컷/ 어드바이스를 인식한다
// - "AOP" 규칙을 담고 있다는 표시일 뿐, 스프링이 관리하는 빈으로 등록해주지 않는다.
// - 따라서 Component를 직접 붙여준다
// - 스프링 컨테이너에 빈으로 등록해야, 스프링이 이 Aspect를 찾아서 실제로 동작한다.

@Aspect
@Component
public class LoggingAspect {
}
