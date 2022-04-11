package goingmerry.cent.jwt;

import goingmerry.cent.domain.User;
import goingmerry.cent.exception.AccountException;
import goingmerry.cent.exception.ErrorType;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 3 * 60 * 1000L; // 3분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L; // 7일

//    private final JwtTokenResolver jwtTokenResolver;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


    @Transactional
    //AccessToken 생성
    public String createAccessToken(User user) {
        Date now = new Date();
        Date accessTokenExpireIn = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME);

        //페이로드에 남길 정보들 (Id, loginId, role)
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole());

        // Access Token 생성
        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setClaims(claims)
                .setExpiration(accessTokenExpireIn)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    @Transactional
    //RefreshToken 생성
    public String createRefreshToken() {
        Date now = new Date();

        Date refreshTokenExpireIn = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setHeaderParam("type","JWT")
                .setExpiration(refreshTokenExpireIn)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

//    @Transactional
//    //RefreshToken 업데이트
//    public String updateRefreshToken(Long userIdx) {
//        Date now = new Date();
//
//        Date refreshTokenExpireIn = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME);
//
//        String newRefreshToken =  Jwts.builder()
//                .setHeaderParam("type","JWT")
//                .setExpiration(refreshTokenExpireIn)
//                .signWith(SignatureAlgorithm.HS512, secretKey)
//                .compact();
//
//        refreshTokenRepository.updatePayload(newRefreshToken, userIdx);
//
//        return newRefreshToken;
//    }
//
//    public Authentication getAuthentication(String token) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(this.jwtTokenResolver.getId(token)));
//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//    }
}