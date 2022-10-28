package SPOTY.Backend.global.jwt;

import SPOTY.Backend.domain.user.domain.Role;
import SPOTY.Backend.domain.user.domain.User;
import SPOTY.Backend.global.exception.domain.user.ForbiddenUser;
import SPOTY.Backend.global.exception.global.BadRequestToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.transaction.Transactional;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
@Transactional
public class TokenService {

    private final Base64.Decoder decoder = Base64.getUrlDecoder();

    @Value("${spring.jwt.secretKey}")
    private String SECRET;
    private final int EXPIRE_SECONDS = 60 * 60;
    private final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
    private SecretKeySpec SECRET_KEY;

    @PostConstruct
    public void init() {
        this.SECRET_KEY = new SecretKeySpec(SECRET.getBytes(), ALGORITHM.getJcaName());
    }

    /**
     * 유저 데이터를 JWT로 암호화 하는 함수.
     * @param user User 객체
     * @return String 생성된 JWT String
     */
    public String encode(User user) {
        Date now = new Date();
        return Jwts.builder()
                //따로 config 로 빼줘도 좋음 - JWT 라는 value 값
                .setHeaderParam("type", "JWT")
                .setIssuer("SPOTY")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(EXPIRE_SECONDS).toMillis()))
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .signWith(ALGORITHM, SECRET_KEY)
                .compact();
    }

    /**
     * JWT String 을 Map 으로 파싱해주는 함수.
     * @param token JWT String
     * @return Map<String, Object> key, value 형태의 Map 데이터
     * @throws ParseException 파싱 실패 예외처리
     */
    public Map<String, Object> parse(String token) throws ParseException {
        String[] chunks = token.split("\\.");
        String jwtBodyString = new String(decoder.decode(chunks[1]));
        JSONParser parser = new JSONParser(jwtBodyString);
        return parser.parseObject();
    }

    public boolean isAdmin(Map<String, Object> payload) {
        String role = payload.get("role").toString();
        if (role.equals(Role.ROLE_ADMIN.toString())) {
            return Boolean.TRUE;
        }
        throw new ForbiddenUser();
    }

    public boolean isUser(Map<String, Object> payload) {
        String role = payload.get("role").toString();
        if (role.equals(Role.ROLE_USER.toString())) {
            return Boolean.TRUE;
        }
        throw new ForbiddenUser();
    }

    public void checkExpToken(String token) throws RuntimeException {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        Date expiration = claims.getExpiration();
        Date current = new Date();

        if (expiration.before(current)){
            throw new BadRequestToken();
        }
    }


    public boolean checkValidateToken(String token) {
        String[] chunks = token.split("\\.");
        String tokenWithoutSignature = chunks[0] + "." + chunks[1];
        String signature = chunks[2];
        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(ALGORITHM, SECRET_KEY);
        if (!validator.isValid(tokenWithoutSignature, signature)) {
            throw new BadRequestToken();
        }
        return true;
    }
}
