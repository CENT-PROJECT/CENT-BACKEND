package goingmerry.cent.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

    // User 400 Error
    USER_JOIN_FAILED("USER003", "회원 가입에 실패 했습니다. 관리자에게 문의하세요", HttpStatus.BAD_REQUEST),

    TOKEN_EXPIRED("USER009", "유효기간이 만료된 토큰입니다.", HttpStatus.BAD_REQUEST),
    USER_RESTRICTED("USER011", "접근 권한이 없는 사용자 입니다. 관리자에게 문의하세요.", HttpStatus.FORBIDDEN), //403

    // Security 401 Error
    UNAUTHENTICATED("SECURITY001", "로그인이 필요한 기능입니다.", HttpStatus.UNAUTHORIZED), //401
    UNAUTHORIZED("SECURITY002", "권한이 없습니다.", HttpStatus.UNAUTHORIZED),
    LOGIN_REQUIRED("SECURITY004", "세션이 만료되었습니다. 다시 로그인 하세요.", HttpStatus.UNAUTHORIZED),
    TOKEN_IS_NOT_FOUND("SECURITY005", "토큰이 만료되었거나, 유효하지 않습니다. 다시 로그인 해주세요", HttpStatus.UNAUTHORIZED),

    JSON_WEB_TOKEN_IS_NOT_VALIDATED("SECURITY006", "토큰이 유효하지 않습니다. 다시 로그인 해주세요", HttpStatus.FORBIDDEN), //403

    // Param
    PARAM_VALID_ERROR("PARAM001", "Exception Message", HttpStatus.BAD_REQUEST),

    // METHOD
    METHOD_NOT_ALLOWED("METHOD001", "Exception Message", HttpStatus.METHOD_NOT_ALLOWED); // 405


    private final String code;
    private final String message;
    private final HttpStatus status;

}