package goingmerry.cent.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

    @Getter
    @NoArgsConstructor
    public static class LoginRequestDto {
        private String email;
        private String password;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserSaveDto {

        private String userName;

        private String nickName;

        private String email;   //ID

        private String password;    //Password

        private String activityArea;

        private String position;

        private boolean isExpert;

        private String gender;

        private String birthDate;

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
