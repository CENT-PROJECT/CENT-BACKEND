package goingmerry.cent.email;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class EmailAuth {

    private static final Long MAX_EXPIRE_TIME = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    //UUID
    private String authToken;
    //만료여부
    private Boolean expired;

    private LocalDateTime expireDate;

    @Builder
    public EmailAuth(String email, String authToken, Boolean expired) {
        this.email = email;
        this.authToken = authToken;
        this.expired = expired;
        //만료 시간 5분 설정
        this.expireDate = LocalDateTime.now().plusMinutes(MAX_EXPIRE_TIME);
    }

    public void useToken() {
        this.expired = true;
    }
}
