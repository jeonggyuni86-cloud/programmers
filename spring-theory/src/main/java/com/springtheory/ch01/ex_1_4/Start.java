package com.springtheory.ch01.ex_1_4;

// * 문제점
// 즉 어떤 ConnectionMaker 구현 클래스를 사용할 지를 결정하는 기능얼 엉겁결에 떠맡았다
// 즉 테스트 클라이언트한테 너무 많은 책임
// 원래 Start.java는 UserDAO가 잘 동작하려 테스트하려고 만든 것이지만
// 그럼에도 다른 책임까지 떠맡고 있으니 문제가 있다

// '오브젝트 팩토리'
// 객체의 생성 방법을 결정하고, 그렇게 만들어진 오브젝트를 돌려주는 일을 하는 것을 '팩토라'라 한다

// * 제어의 역전
// 제어의 역전이란 오브젝트가 자신이 사용할 오브젝트를 스스로 선택 및 생성 하지 않고
// 또 자신이 언제 어떻게 만들어지는지 조차 모른 채 그 제어권을 외부에 넘기는 것

// UserDAO 입장에서 두가지 제어권이 사라졌다.
// 1. 어떤 ConnectionMaker를 사용할지 에 대한 제어권
//  public UserDAO(SimpleConnectionMaker simpleConnectionMaker) {
//      this.simpleConnectionMaker = simpleConnectionMaker;
//  }

// 2. 자기 자신에 생성에 대한 제어권
//  public UserDAO userDao() {
//      UserDAO userDAO = new UserDAO(connectionMaker());  // 생성 + 관계설정을 팩토리가 담당
//      return userDAO;
//  }
//  private SimpleConnectionMaker connectionMaker() {
//      return new DConnectionMaker();
//  }
// -> UserDAO가 언제, 어떤 의존성을 함께 만들어지는지를 DaoFactory가 결정한다.
// 즉, 어떤 구현을 쓸지 결정하고 오브젝트 생성 및  연결 하는 제어 권한이 UserDAO에서 DaoFactory 에게로 넘어갔다.
// 이것이 '제어의 역전' 이다.

//  원래 테스트용이던 클라이언트가 "어떤 ConnectionMaker를 쓸지 결정하는 책임"까지 떠맡던 문제를, 팩토리를 도입해
//  "컴포넌트 역할 오브젝트"와 "구조를 결정하는 오브젝트"를 분리한 것이다.

// 템플릿 메서드는 제어의 역전이라는 개념을 활용해 문제를 해결하는 디자인 패턴이라고 볼 수 있다.

// 스프링은 제어의 역전을 모든 기능의 기초가 되는 기반기술로 삼고 있으며,
// 제어의 역전을 극한까지 사용하는 프레임워크이다.


import com.springtheory.ch01.ex_1_4.dao.DaoFactory;

public class Start {
    static void main(String[] args) {
        var dao = new DaoFactory().getUserDAO();
    }
}
