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
}
