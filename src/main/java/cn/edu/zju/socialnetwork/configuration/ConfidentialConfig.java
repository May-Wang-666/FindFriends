package cn.edu.zju.socialnetwork.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ConfidentialConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ConfidentialInterceptor()).addPathPatterns("/**");
    }
}
/*@Configuration
public class ConfidentialConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        // 一定要指定对应的域名 不能是 `*` 这样的通配符
                        // 也可以是 http://localhost:8989 这样的域名
                        .allowedOrigins("https://segmentfault.com")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        // 必选true
                        .allowCredentials(true)
                        .maxAge(3600);

            }
        };
    }
}*/
