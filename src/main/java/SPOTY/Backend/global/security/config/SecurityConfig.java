package SPOTY.Backend.global.security.config;

import SPOTY.Backend.domain.user.oauth.OAuth2AuthenticationSuccessHandler;
import SPOTY.Backend.global.config.CorsConfig;
import SPOTY.Backend.global.jwt.TokenService;
import SPOTY.Backend.global.security.CustomUserDetailService;
import SPOTY.Backend.global.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CorsConfig corsConfig;

    private final TokenService tokenService;

    private final CustomUserDetailService userDetailsService;

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .addFilter(corsConfig.corsFilter())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .httpBasic().disable()
                .formLogin().disable()

                .authorizeRequests()
//                .antMatchers("/api/join/mail").permitAll()
//                .antMatchers("/api/**").hasAnyRole("USER", "ADMIN")// role 문제로 403, 일단 주석처리
//                .antMatchers("/api/**").hasRole("ADMIN")// 테스트 시 path 관리할 것
                .anyRequest().permitAll()

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(tokenService, userDetailsService),
                        UsernamePasswordAuthenticationFilter.class)


                .oauth2Login().permitAll()
//                    .defaultSuccessUrl("/")
                    .userInfoEndpoint()
                        .userService(userDetailsService)
                .and()
                    .successHandler(oAuth2AuthenticationSuccessHandler);


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
