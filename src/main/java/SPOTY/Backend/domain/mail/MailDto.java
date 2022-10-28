package SPOTY.Backend.domain.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

public class MailDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MailRequestDto {
        @Email(message = "이메일 형식을 확인해주세요.")
        private String mail;
    }


}
