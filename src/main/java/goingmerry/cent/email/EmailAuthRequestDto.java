package goingmerry.cent.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailAuthRequestDto {
    String email;
    String authToken;

    public EmailAuthRequestDto(String email, String authToken) {
        this.email = email;
        this.authToken = authToken;
    }
}
