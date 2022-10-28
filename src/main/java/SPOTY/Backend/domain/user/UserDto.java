package SPOTY.Backend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

public class UserDto {

    @Getter
    @NoArgsConstructor
    public static class LoginRequestDto {
        private String email;
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSaveDto {

        private String email;

        private String username;

        private String nickname;

        private String password;

        private String state;

        private String city;

        private String preferredPosition;

        private String isExpert;

        private String birthDate;
    }
}
