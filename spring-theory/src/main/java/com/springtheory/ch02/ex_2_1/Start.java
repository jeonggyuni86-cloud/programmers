package com.springtheory.ch02.ex_2_1;

import com.springtheory.ch02.ex_2_1.domain.User;
import com.springtheory.ch02.ex_2_1.dao.DaoFactory;
import com.springtheory.ch02.ex_2_1.dao.UserDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

// * 문제점
// start.java의 문제점
// - 수동 확인 작업의 번거로움
// 콘솔에 나온 값을 보고 등록과 조회가 성공적으로 되고 있는지를 확인하는 건 사람의 책임이다.

// - 실행 작업의 번거로움
// 만약 DAO가 수백 개가 되고 그에 대한 main() 메서드도 그만큼 만들어진다면,
// 전체 기능을 테스트해보기 위해 main() 메서드를 수백 번 실행하는 수고가 필요하다.

// * 단위 테스트
// 테스트는 가능하면 작은 단위로 쪼개서 집중해서 할 수 있어야 한다.
// 관심사의 분리라는 원리가 여기에도 적용된다.
// 단위 테스트를 하는 이유는 개발자가 설계하고, 만든 코드가 원래 의대혼 대로 동작하는지를
// 개발자 스스로 빨리 확인받기 위해서다.

// * 자동수행 테스트 코드
// 테스트는 자동으로 수행되도록 코드로 만들어주는 것이 중요하다.
// 애플리케이션을 구성하는 클래스 안에 테스트 코드를 포함시키는 것보다는
// 별도로 테스트용 클래스를 만들어서 테스트 코드를 넣는 편이 낫다.
// 자동을 수행되는 테스트의 장점은 자주 반복될 수 있다는 것이다.

// * 테스트의 결과
// 모든 테스트는 성공과 실패의 두 가지 결과를 가질 수 있다.
// 또 테스트의 실패는 테스트가 진행되는 동안에 에러가 발생해서 실패한 경우와,
// 테스트 작업 중에 에러가 발생하진 않았지만 그 결과가 기대한 것과 다르게 나오는 경우로 구분해볼 수 있다.

// * Junit 프레임워크 사용
// JUnit은 자바에서 단위 테스트를 자동으로 작성·실행하게 해주는 표준 테스트 프레임워크다.
// 위 Start.java처럼 main()으로 직접 실행하고 콘솔을 눈으로 확인하던 방식을 대체한다.


public class Start {
    static void main(String[] args) throws SQLException, ClassNotFoundException {

        var context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDAO userDao = context.getBean("userDAO", UserDAO.class);

        User user = userDao.get("test1");
        System.out.println(user.toString());

    }
}
