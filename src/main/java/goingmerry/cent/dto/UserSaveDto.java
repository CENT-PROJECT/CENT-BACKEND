package goingmerry.cent.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserSaveDto {

    private String userName;

    private String nickName;

    private String email;   //ID

    private String password;    //Password

    private String activityArea;

    private String position;

    private boolean isExpert;

    private String gender;

    private String birthDate;

    public UserSaveDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
