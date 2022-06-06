package goingmerry.cent.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String accessToken;

    @Builder
    public LoginResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

}
