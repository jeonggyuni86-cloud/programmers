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


public class Start {
    static void main(String[] args) {

    }
}
