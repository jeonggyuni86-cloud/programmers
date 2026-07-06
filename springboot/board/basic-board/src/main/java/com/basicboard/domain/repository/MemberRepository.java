package com.basicboard.domain.repository;

import com.basicboard.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 이 인터페이스에는 구현 클래스가 없다
// - 우리는 interface만 선언하고 구현체(class)는 만들지 않는다.
// - 애플리케이션이 뜰 때 Spring Data JPA가 이 인터페이스의 구현체를 프록시로 자동으로 Bean으로 등록한다
// - 그래서 서비스 memberRepository를 주입받아 바로 쓸 수 있는 것이다.

// * JpaRepository<Member, Long>의 두 타입 파라미터
// - Member : 이 레포지토리가 다루는 엔티티 타입
// - Long : 이 엔티티의 기본(@Id) 타입
// - 이것만 상속해도 기본 CRUD 메서드가 공짜로 딸려온다.
// save(entity) : 저장 / 수정기능
// findById(id) : 기본키로 1건 조회 (PK 유일성) -> Optional 반환
// findAll() : 전체 조회
// delete(entity) : 삭제

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByUserId(String userId);

    Optional<Member> findByUserId(String userId);
}
