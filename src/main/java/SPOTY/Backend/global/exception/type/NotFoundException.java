package SPOTY.Backend.global.exception.type;

import goingmerry.cent.exception.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * HttpStatus 가 NOT_FOUND 인 예외들의 부모 클래스.
 */
@Getter
public class NotFoundException extends BaseException {

    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    /**
     * NotFoundException 생성자.
     */
    public NotFoundException() {
    }

}
