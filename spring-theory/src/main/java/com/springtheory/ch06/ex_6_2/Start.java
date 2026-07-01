package com.springtheory.ch06.ex_6_2;

// * 스프링 프록시 팩토리 빈
// * 문제점
// 다이나믹 프록시를 우리가 직접 다뤘다.
// DaoFactory.userService() Bean 안에서 Proxy.newProxyInstance를 직접 호출해서 프록시를 얻었고,
// TransactionHandler도 직접 작성해야 한다.
// 게다가 '어떤 메서드에 적용할지(패턴)'도 핸들러 안에 섞여 있다.
//   구체적으로 TransactionHandler(ex_6_1)의 두 부분이 그렇다:
//     (1) private String pattern;                          // '어디에 적용할지' 정보를 핸들러가 직접 보유
//     (2) if (method.getName().startsWith(pattern)) { ... } // invoke() 안에서 적용 대상을 직접 판단
// 즉, '무엇을 할지(트랜젝션)'와 '어디에 걸지' 가 한 클래스에 엉켜있었다.


// 스프링의 'ProxyFactoryBean'
//   Advisor = Pointcut(어디에) + Advice(무엇을) 로 분리한다.
//     - startsWith(pattern) 같은 선정 로직 -> Pointcut 객체로 분리
//     - 트랜잭션 거는 로직            -> Advice 객체로 분리
//   target과 advisor만 등록하면 스프링이 프록시를 알아서 만들어주고, 둘을 독립적으로 갈아끼우고 조합할 수 있다.

// - 스프링 AOP의 구성 개념
// - Advice             (무엇을) : 부가기능 자체 (예: TransactionAdvice)
// - Pointcut           (어디에) : 부가기능을 적용할 메서드 선별 (예: NameMatchMethodPointcut("upgrade*"))
// - Advisor                   : Advice + pointcut 묶음 (예: DefaultPointcutAdvisor)
// - ProxyFactoryBean          : target + advisor를 받아 '프록시'를 생산하는 스프링 팩토리 빈

public class Start {
    static void main(String[] args) {

    }
}
