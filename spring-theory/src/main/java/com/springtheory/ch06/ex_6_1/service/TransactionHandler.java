package com.springtheory.ch06.ex_6_1.service;

// * TransactionHandler - 다이나믹 프록시의 '부가기능' 담당
// - InvocationHandler
// 다이나믹 프록시에 '메서드 호출이 들어오면 무엇을 할지' 정의해주는 인터페이스
// 메서드는 invoke() 하나 뿐이다. 프록시로 들어오는 '모든' 메서드 호출이 이 invoke 메서드이다.


// - 리플렉션(Reflection)
// 리플렉션은 '프로그램이 실행 중(런타임)에, 자기 자신의 클래스 / 메서드 / 필드 정보를 들여다 보고, 다률 수 있게 해주는 기능'이다.
//   - 보통 우리는 컴파일 시점에 정해진 대로 호출한다:  userService.upgradeLevels();
//   - 리플렉션은 '메서드를 값(객체)처럼' 다룬다. java.lang.reflect.Method 객체를 받아서
//     실행 중에 method.invoke(대상, 인자)로 호출한다. -> 어떤 메서드인지 미리 몰라도 호출 가능.
//   다이내믹 프록시가 바로 이 리플렉션 위에서 동작한다.

import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TransactionHandler implements InvocationHandler {

    // 부가기능을 적용할 실제 오브젝트(ex: UserServiceImpl)
    private final Object target;

    // 트렌젝션 추상화
    private final PlatformTransactionManager transactionManager;

    // 이 이름으로 시작하는 메서드에만 트렌젝션 적용
    private final String pattern;

    public TransactionHandler(Object target, PlatformTransactionManager transactionManager, String pattern) {
        this.target = target;
        this.transactionManager = transactionManager;
        this.pattern = pattern;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 호출된 메서드 '이름'이 패턴으로 지작하면 트렌젝션으로 감싼다.
        // 예) pattern = "upgrade" 이면 upgradeLevels()는 매칭, add는 매칭X
        if (method.getName().startsWith(pattern)) {
            // 트렌젝션 경계설정

        }


        return method.invoke(target, args);
    }
}
