package SPOTY.Backend.global.jwt;

import SPOTY.Backend.domain.user.domain.Role;
import SPOTY.Backend.global.exception.BaseException;
import SPOTY.Backend.global.exception.domain.user.ForbiddenUser;
import SPOTY.Backend.global.exception.global.BadRequestToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
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
import javax.servlet.http.HttpServletRequest;
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

    //30분
    private final int ACCESS_TOKEN_EXPIRE_SECONDS = 60 * 30;
    //7일
    private final int REFRESH_TOKEN_EXPIRE_DAYS = 7;
    private final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS256;
    private SecretKeySpec SECRET_KEY;

    @PostConstruct
    public void init() {
        this.SECRET_KEY = new SecretKeySpec(SECRET.getBytes(), ALGORITHM.getJcaName());
    }

    private JwtBuilder createToken(CreateTokenDto dto) {
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setIssuer("SPOTY")
                .claim("id", dto.getUserId())
                .claim("role", dto.getRole())
                .signWith(ALGORITHM, SECRET_KEY);
    }

    public String createAccessToken(CreateTokenDto dto) {
        Date now = new Date();
        JwtBuilder jwtBuilder = createToken(dto);
        return jwtBuilder
                .setExpiration(new Date(
                        now.getTime() + Duration.ofMinutes(ACCESS_TOKEN_EXPIRE_SECONDS).toSeconds()
                ))
                .compact();
    }

    public String createRefreshToken(CreateTokenDto dto) {
        Date now = new Date();
        JwtBuilder jwtBuilder = createToken(dto);
        return jwtBuilder
                .setExpiration(new Date(
                        now.getTime() + Duration.ofMinutes(REFRESH_TOKEN_EXPIRE_DAYS).toDays()
                ))
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

    // Request의 Header에서 token 값을 가져옵니다.
    // "Authorization" : <Bearer> "TOKEN'
    public String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        try {
            return authorization.split(" ")[1];
        } catch (BaseException e) {
            throw new BadRequestToken();
        }
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

    public void validateToken(String token) {
    }

    private boolean IsExpired(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        Date expiration = claims.getExpiration();
        Date current = new Date();

        if (expiration.before(current)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    private boolean checkTokenSignature(String token) {
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
