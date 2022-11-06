package SPOTY.Backend.global.exception.type;

import SPOTY.Backend.global.exception.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * HttpStatus 가 Conflict 인 예외들의 부모 클래스.
 * status 409
 */
@Getter
public class ConflictException extends BaseException {

    private final HttpStatus httpStatus = HttpStatus.CONFLICT;

    /**
     * ConflictException 생성자.
     */
    public ConflictException() {
    }
}
