package goingmerry.cent.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateDto {

    private Long id;

    private String nickName;

    private String activityArea;

    private String Position;

    private boolean isExpert;

    private String ageRange;

}
