package goingmerry.cent.config;

import goingmerry.cent.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //== 접근 제한 ==//
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/oauth2/*").permitAll() //로그인 화면 접근 가능
                .antMatchers("/").permitAll() //메인 화면 접근 가능
//              .anyRequest().hasRole("USER");
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);

    }



}
