package goingmerry.cent.response_format;

import goingmerry.cent.response_format.Result;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResult<T> extends Result {
    private T data;
}
