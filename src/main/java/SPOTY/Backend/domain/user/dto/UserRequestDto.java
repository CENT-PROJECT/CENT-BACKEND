package SPOTY.Backend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

public class UserRequestDto {

    @Getter
    @NoArgsConstructor
    public static class LoginRequestDto {
        private String email;
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinRequestDto {

        private String email;

        private String code;

        private String username;

        private String nickname;

        private String password;

        private String state;

        private String city;

        private String preferredPosition;

        private String isExpert;

        private String birthDate;

        public void setPassword(String password) {
            this.password = password;
        }
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SocialJoinRequestDto {

        private UUID id;

        private String email;

        private String nickname;

        private String state;

        private String city;

        private String preferredPosition;

    }
}
