package goingmerry.cent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CustomCorsFilter {

    //<-> 컨트롤러 단에서 @CrossOrigin
    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        //자격증명과 함께 요청 여부 (Authorization로 사용자 인증 사용 시 true)
        config.addAllowedOrigin("*");   //모든 IP 에 응답을 허용하겠다. //?? credentials 설정하면 Origin 특정해줘야 한다??
        config.addAllowedHeader("*");   // 모든 header에 응답을 허용하겠다.
        config.addAllowedMethod("*");   //모든 post, get , put, delete , patch 등의 요청을 허용하겠다.
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

}
