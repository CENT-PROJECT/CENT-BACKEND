package SPOTY.Backend.domain.mail;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@Getter
@RedisHash("mail")
public class Mail {

    public static final int DEFAULT_TTL_SECONDS = 300;

    @Id
    private String id;

    @Indexed
    private String code;

    @TimeToLive
    private int expiration = DEFAULT_TTL_SECONDS;

    @Builder
    public Mail(String id, String code) {
        this.id = id;
        this.code = code;
    }
}
