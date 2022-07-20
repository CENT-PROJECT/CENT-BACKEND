package goingmerry.cent;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SingleResponseData {
    Object data;

    public SingleResponseData(Object data) {
        this.data = data;
    }
}
