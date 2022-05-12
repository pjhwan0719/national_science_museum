package com.avad.nationalScienceMuseum.exception.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    ALREADY_LOGIN_IN(HttpStatus.BAD_REQUEST, "이미 로그인된 사용자입니다."),
    DUPLICATE_ID(HttpStatus.BAD_REQUEST, "이미 사용중인 아이디입니다."),
    FILE_EMPTY(HttpStatus.BAD_REQUEST, "파일이 없습니다."),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    NOT_FOUND_COUNSELOR(HttpStatus.NOT_FOUND, "상담사 정보를 찾을 수 없습니다"),
    NOT_FOUND_KIOSK(HttpStatus.NOT_FOUND, "KIOSK 정보를 찾을 수 없습니다"),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다"),

    /* 500 서버 내부 오류 */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류")
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}
