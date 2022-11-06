package SPOTY.Backend.global.exception.global;

import SPOTY.Backend.global.exception.type.BadRequestException;
import SPOTY.Backend.global.exception.type.UnAuthorizedException;
import org.springframework.http.HttpStatus;

public class UnAuthorizedToken extends UnAuthorizedException {

    private final String errorCode = "UNAUTHORIZED_TOKEN";
    private final String message = "토큰 재발급을 해주세요.";
    private HttpStatus httpStatus;

    /**
     * BadRequestToken 생성자.
     */
    public UnAuthorizedToken() {
        super();
        this.httpStatus = super.getHttpStatus();
    }
}
