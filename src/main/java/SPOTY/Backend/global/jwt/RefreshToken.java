package SPOTY.Backend.global.jwt;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.util.concurrent.TimeUnit;

@Getter
@RedisHash("token")
public class RefreshToken {

    public static final int DEFAULT_TTL_DAY = 7;

    @Id
    @Indexed
    private String id;

    @Indexed
    private String token;

    @TimeToLive(unit = TimeUnit.DAYS)
    private int expiration = DEFAULT_TTL_DAY;

    @Builder
    public RefreshToken(String id, String token) {
        this.id = id;
        this.token = token;
    }
}
