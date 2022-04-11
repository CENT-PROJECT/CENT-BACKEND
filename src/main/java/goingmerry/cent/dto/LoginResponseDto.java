package goingmerry.cent.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String accessToken;
    private boolean isJoined;

    @Builder
    public LoginResponseDto(String accessToken, boolean isJoined) {
        this.accessToken = accessToken;
        this.isJoined = isJoined;
    }
}
