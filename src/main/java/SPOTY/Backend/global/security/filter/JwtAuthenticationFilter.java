package SPOTY.Backend.global.security.filter;

import SPOTY.Backend.global.exception.BaseException;
import SPOTY.Backend.global.jwt.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 매 요청마다 JWT 가 유효한지 검증하고, 유효할 시 해당 유저에 Security Context 를 인가 해주는 필터
 */
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String[] whitelist = {"/", "/api/join/**", "/api/login"};

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        //whiteList 처리
        if (checkIsWhitelist(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 헤더가 필요한 요청에 대하여 헤더가 비어있을 때 - 시작
        if (request.getHeader("Authorization") == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 헤더에서 JWT 를 받아옵니다.
        String token = tokenService.getToken(request);

        try {
            tokenService.validateToken(token);
        } catch (BadCredentialsException | SignatureException | BaseException | ExpiredJwtException ex) {
            filterChain.doFilter(request, response);
            return;
        }
        // 해당 AccessToken Payload 유효하다면 인가 및 인증객체 저장
        String loginId = jwtResolver.jwtResolveToUserLoginId(token);

        try {
            UserDetails requestUserDetails = userDetailsService.loadUserByUsername(loginId);
        } catch (CustomException | NoSuchElementException e) {
            JSONObject responseJson = new JSONObject();
            try {
                responseJson.put("code", HttpServletResponse.SC_BAD_REQUEST);
                responseJson.put("message", ErrorCode.USER_NOT_EXIST.toString());
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print(responseJson);
            return;
        }

        UserDetails requestUserDetails = userDetailsService.loadUserByUsername(loginId);

        // JWT 를 바탕으로 인증 객체 생성
        Authentication authToken =
                new UsernamePasswordAuthenticationToken(requestUserDetails.getUsername(), requestUserDetails.getPassword());
        // Authorities 부여
        Authentication auth = authenticationManager.authenticate(authToken);

        // SecurityContextHolder 에 저장
        SecurityContextHolder.getContext().setAuthentication(auth);

        filterChain.doFilter(request, response);
    }
    }

    /**
     * whiteList 일 경우, true 를 반환한다.
     */
    private boolean checkIsWhitelist(String requestURI) {
        return PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

}
