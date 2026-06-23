package com.springtheory.ch01.ex_1_4;

// * 문제점
// 즉 어떤 ConnectionMaker 구현 클래스를 사용할 지를 결정하는 기능얼 엉겁결에 떠맡았다
// 즉 테스트 클라이언트한테 너무 많은 책임
// 원래 Start.java는 UserDAO가 잘 동작하려 테스트하려고 만든 것이지만
// 그럼에도 다른 책임까지 떠맡고 있으니 문제가 있다

// '오브젝트 팩토리'
// 객체의 생성 방법을 결정하고, 그렇게 만들어진 오브젝트를 돌려주는 일을 하는 것을 '팩토라'라 한다


public class Start {
    static void main(String[] args) {

    }
}
