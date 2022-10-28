package SPOTY.Backend.global.exception.domain.mail;

import SPOTY.Backend.global.exception.type.BadRequestException;
import org.springframework.http.HttpStatus;

public class BadRequestMail extends BadRequestException {

    private final String errorCode = "BAD_REQUEST_MAIL";
    private final String message = "이메일 인증이 실패하였습니다. 인증을 해주세요.";
    private HttpStatus httpStatus;

    /**
     * BadRequestMail 생성자.
     */
    public BadRequestMail() {
        super();
        this.httpStatus = super.getHttpStatus();
    }
}
