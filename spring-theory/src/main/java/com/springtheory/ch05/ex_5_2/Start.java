package com.springtheory.ch05.ex_5_2;

// * 트랜젝션 서비스 추상화
// * 문제점
// upgradeLevel()이 하나씩 upgrade 할때 중간에 실패하면,
// 일부만 반영되는 '부분 실패'가 생긴다 (원자성 미포함)

// '트랜젝션'을 통해 해결
// 여러 update를 '하나의 트랜젝션' 으로 묶고 실패 시 전부 롤백한다.

public class Start {

    static void main(String[] args) {


    }
}
