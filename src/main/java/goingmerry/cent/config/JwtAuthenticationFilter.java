package goingmerry.cent.config;

import goingmerry.cent.exception.type.ForbiddenException;
import goingmerry.cent.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private static final String[] whitelist = {"/", "/api/login", "/api/join", "/api/sign/verify-email", "/error"};
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();

        //whiteList 처리
        if (checkIsWhitelist(requestURI)) {
            chain.doFilter(request, response);
            return;
        }
        // 헤더에서 JWT 를 받아옵니다.
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        log.info("User Token : {}", token);

        if (jwtTokenProvider.validateToken(token) && token != null) {

            log.info("User validation success");

            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            // SecurityContext 에 Authentication 객체를 저장합니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } else {
//        throw new ServletException("토큰 유효성 검사 실패");
            throw new ForbiddenException();
        }

    }

    /**
     * whiteList의 경우 인증 체크를 안하도록 한다.
     */
    private boolean checkIsWhitelist(String requestURI) {
        return PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}






