package SPOTY.Backend.global.security.filter;

import SPOTY.Backend.global.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

        // 헤더에서 JWT 를 받아옵니다.
        String token = tokenService.getToken(request);

        if (token != null) {

            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
//            Authentication authentication = jwt.getAuthentication(token);
            // SecurityContext 에 Authentication 객체를 저장합니다.
//            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }
        throw new ServletException("토큰 유효성 검사 실패");
    }

    /**
     * whiteList 일 경우, true 를 반환한다.
     */
    private boolean checkIsWhitelist(String requestURI) {
        return PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

}
