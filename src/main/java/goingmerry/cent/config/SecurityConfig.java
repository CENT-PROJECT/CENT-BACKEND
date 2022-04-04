package goingmerry.cent.config;

import goingmerry.cent.oauth.OAuth2AccessTokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2AccessTokenAuthenticationFilter oAuth2AccessTokenAuthenticationFilter;
    private final CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //== 접근 제한 ==//
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션을 사용하지 않겠다!
                .and()
                .addFilter(corsFilter)
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/login/oauth2/*").permitAll() //로그인 화면 접근 가능
                .antMatchers("/").permitAll(); //메인 화면 접근 가능

//              .anyRequest().hasRole("USER");

        http.addFilterBefore(oAuth2AccessTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }



}
