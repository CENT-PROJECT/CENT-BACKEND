package goingmerry.cent.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String accessToken;
    private boolean isJoined = true;

    @Builder
    public LoginResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setJoined(boolean joined) {
        isJoined = joined;
    }
}
