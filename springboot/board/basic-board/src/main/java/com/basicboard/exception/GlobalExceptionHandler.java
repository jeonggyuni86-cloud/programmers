package com.basicboard.exception;

import com.basicboard.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;


// @RestControllerAdvice 란
// - '모든 컨틀롤러에 공통으로 적용되는 보조 클래스' 임을 선언하는 어노테이션
// - 특정 컨틀롤러 1개가 아닌, 애플리케이션의 '모든 @Controller, @RestController 에서 발생하는 예외'를 가로챈다

// * 전역 예외 처리
// - 예외가 터질 때 마다 컨트롤러 안에서 try - catch로 일일이 잡으면, 컨트롤러마다 같은 코드가 반복된다.
// - 핵심 로직과 예외 처리가 코드가 뒤섞여서 지저분해지고, 응답 형태(상태코드 / 메시지)가 제각각이 되기 쉽다.
// - 그래서 "예외 처리" 라는 공통 관심사를 한 곳에 모아두고, 컨틀롤러/서비스는 예외를 "던지기만" 하게 만든다.
// -> 컨틀롤러는 성공 흐름(정상 로직)에만 집중하고, 예외 -> 응답 변환은 전부 이 클래스가 책임진다.

// * 전체 흐름
//   서비스: throw new DuplicateUserIdException("이미 존재하는 아이디입니다.")
//     -> (컨트롤러는 잡지 않고 그대로 위로 전파됨)
//        -> 여기 GlobalExceptionHandler 가 가로챔
//           -> 상태코드(409) + ErrorResponseDto(JSON) 로 변환해 응답
//              -> signUp.js 의 error 콜백이 message 를 꺼내 화면에 표시

// * 그런데 예외 처리도 "공통 관심사" 인데 AOP가 아니라 @RestControllerAdvice를 쓰는가?
// - 사실 @RestControllerAdvice 내부적으로 아이디어는 AOP와 같은 "공통 관심사 분리" 아이디어 위에 서 있다.
// - "범용 API를 직접 만들것인가 vs 웹 예외 처리에 특화된 전용 도구를 쓸것인가" 의 문제이다.

// 1) AOP는 "컨트롤러 메서드 호출" 그 순간만 감쌀 수 있다.
// - @Around는 컨트롤러 메서드가 실제로 실행되는 지점만 try-catch로 감싼다
// - 하지만 웹 예외는 메서드 "바깥"에서도 발생한다. (@Valid 검증 실패, JSON <-> DTO 변환 실패, 파리미터 타입 불일치 등등)
// - 이런 예외는 컨트롤러 메서드가 호출되기도 "전"에 발생해서 AOP 포린트 컷에 잡히지 않는다.
// - @RestControllerAdvice는 스프링 MVC의 예외 처리 파이프라인에 연결되어, 이런 프레임워크 단계의 예외까지 잡아준다.

// 2) 응답 만들기를 프레임워크가 대신 해준다.
// - AOP로 직접 하면 예외을 잡은뒤 상태코드 세팅, JSON 직렬화, Content-Type 협상을 전부 손으로 짜야 한다.
// - @RestControllerAdvice는 ResponseEntity / 메시지 컨버터(DTO 자동 JSON 변환) / 예외 타입 매핑을 이미 제공한다.

// 3) 예외 타입별 분기가 선언적이다.
// - @ExceptionHandler(클래스 타입)로 "이 예외는 이 메서드가"를 어노테이션으로 나눈다.
// - AOP로 하면 if(e instanceOf ...) 분기를 직접 나열해야 해서 읽기 어렵고 유지보수가 나쁘다.

// # 한 줄 요약: 도구를 목적에 맞게
//   - 로깅/실행시간 측정/트랜잭션 처럼 "무엇에나 끼워 넣는 범용 작업"       -> AOP (@Aspect)  [LoggingAspect 참고]
//   - 예외를 적절한 HTTP 응답으로 바꾸는 "웹 특화 작업"                     -> @RestControllerAdvice (이 클래스)
//   (억지로 AOP 로 예외 처리도 되긴 하지만, 위 (1)(2) 를 전부 직접 만들어야 해서 손해다)


@RestControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler : "어떤 예외를 처리할지" 지정한다.
    // - 괄호 안에 적은 예외 타입이 발생하면 스프링이 이 메서드를 자동으로 호출한다.
    // - 메서드 파라미터로 그 예외 객체(e)를 받아, 메세지와 상세 정보를 꺼내 쓸  수 있다.
    // ResponseEntity<T>
    // - HTTP 응답 "전체"를 표현하는 객체이다 - 응답 본문(Body) 뿐만 아니라 "상태 코드"와 헤더까지 적용할 수 있다.
    // - 단순히 DTO만 반환하면 상태 코드가 항상 200(OK)으로 프론트로 돌아오게된다.
    // - 예외 상황에서는 상태코드를 4xx/5xx등으로 바꿔야 하므로, ResponseEntity로 감싼다.

    @ExceptionHandler(DuplicateUserIdException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateUserIdException(DuplicateUserIdException e) {
        return ResponseEntity
                .status(CONFLICT)
                .body(
                        new ErrorResponseDto(CONFLICT.value(), e.getMessage())
                );
    }

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleBoardNotFoundException(BoardNotFoundException e) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(
                        new ErrorResponseDto(NOT_FOUND.value(), e.getMessage())
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> exception(Exception e) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        new ErrorResponseDto(INTERNAL_SERVER_ERROR.value(), "서버 오류가 발생했습니다")
                );
    }
}
