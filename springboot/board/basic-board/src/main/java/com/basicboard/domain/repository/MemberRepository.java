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

// * 쿼리 메서드 (Query Method)
// - 기본 CRUD 외에 '조건이 있는 조회'가 필요할 때, 메서드 '이름'만 규칙대로 지으면
// Spring Data JPA가 그 이름을 해석(파싱)해서 SQL(JPQL)을 자동으로 만들어 준다.
// 즉, 이름이 곧 쿼리다.

// * 네이밍 규칙 구조 : (동사 (반환형태)) + By + (조건이 될 필드명)

// 1. 동사 부분 : "무엇을 / 어떤 형태로" 반환할지 결정
// - find : 엔티티를 조회 예) findByUserId -> SELECT * FROM member WHERE user_id = ?
// - exists : 존재 여부 예) existsByUserId -> true / false
// - count : 개수(long) 예) countByUserName
// - delete : 삭제

// 2. By : '여기서 부터 조건(WHERE)이 시작된다'는 구분자

// 3. 조건 필드 : 엔티티의 "필드 이름"을 그대로 쓴다. (즉 DB 컬럼명인 user_id가 아닌 자바 필드 (userId)로 쓴다)
// -> 대소문자까지 필드와 정확히 일치해야 한다.

// * 여러 조건을 조합할 수도 있다 (규칙만 지키면 이름이 길어져도 동작한다)
//   findByUserIdAndPassword(String userId, String password)  -> WHERE user_id = ? AND password = ?
//   findByUserNameOrderByIdDesc(String userName)             -> WHERE user_name = ? ORDER BY id DESC
//   findByUserNameContaining(String keyword)                 -> WHERE user_name LIKE %?%
//   ...And / Or / Between / LessThan / GreaterThan / Like / Containing / OrderBy... 등 키워드 조합 가능
//   ※ 파라미터의 "개수와 순서"는 이름에 등장하는 조건 필드 순서와 일치해야 한다

// * 이름만으로 안 되는 복잡한 쿼리는?
// - 조인이 많거나 조건이 복잡하면 이름이 감당 안 될 만큼 길어진다
// - 그럴 때는 메서드 위에 @Query("JPQL 또는 SQL") 로 쿼리를 직접 작성한다 (이 클래스엔 아직 필요 없어 생략)

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByUserId(String userId);

    Optional<Member> findByUserId(String userId);
}
