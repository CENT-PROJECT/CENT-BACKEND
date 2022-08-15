package goingmerry.cent.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        WebMvcConfigurer.super.configureViewResolvers(registry);
//        MustacheViewResolver resolver = new MustacheViewResolver();
//        resolver.setCharset("UTF-8");
//        resolver.setContentType("text/html; charset=UTF-8");
//        resolver.setPrefix("classpath:/templates/");
//        resolver.setSuffix(".html");
//
//        registry.viewResolver(resolver);
//    }

    /**
     * CORS 설정을 추가합니다. CORS 에 대한 자세한 설명은 아래 링크부터 시작하세요.
     * https://developer.mozilla.org/ko/docs/Web/HTTP/CORS
     * @param registry 넘겨받은 registry 에 허용하고자 하는 사이트를 추가해줍니다.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        //"http://localhost"
                        "http://localhost:3000"
                )
                .allowedMethods("OPTIONS","GET","POST","PUT","DELETE");
    }
}
