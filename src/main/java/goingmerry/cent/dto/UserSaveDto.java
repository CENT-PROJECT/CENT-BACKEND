package goingmerry.cent.dto;

import lombok.Getter;

@Getter
public class UserSaveDto {
    private String activityArea = "";

    private String nickName;

    private String position;

    private boolean isExpert;

    public UserSaveDto(String activityArea, String nickName, String position, boolean isExpert) {
        this.activityArea = activityArea;
        this.nickName = nickName;
        this.position = position;
        this.isExpert = isExpert;
    }
}
