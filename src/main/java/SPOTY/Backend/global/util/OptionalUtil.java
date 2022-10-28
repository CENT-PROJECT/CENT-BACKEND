package SPOTY.Backend.global.util;

import SPOTY.Backend.global.exception.BaseException;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@NoArgsConstructor
@Component
public class OptionalUtil<T> {

    public T ifEmptyReturnNull(Optional<T> props) {
        if (props.isEmpty()) {
            return null;
        }
        return props.get();
    }

    public T ifEmptyThrowError(Optional<T> props, BaseException exception) {
        if (props.isEmpty()) {
            throw exception;
        }
        return props.get();
    }
}
