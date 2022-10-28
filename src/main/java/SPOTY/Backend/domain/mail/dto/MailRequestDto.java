package SPOTY.Backend.domain.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

public class MailRequestDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SendMailDto {
        @Email(message = "이메일 형식을 확인해주세요.")
        private String mail;
    }
}
