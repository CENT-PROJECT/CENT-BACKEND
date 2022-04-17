package goingmerry.cent.dto;

import lombok.Getter;

@Getter
public class UserSaveDto {
    private String activityArea = "";

    private String Position;

    private boolean isExpert;

    public UserSaveDto(String activityArea, String position, boolean isExpert) {
        this.activityArea = activityArea;
        Position = position;
        this.isExpert = isExpert;
    }
}
