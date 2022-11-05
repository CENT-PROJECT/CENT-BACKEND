package SPOTY.Backend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponseDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponseDto {
        private String accessToken;
        private String refreshToken;
    }
}
