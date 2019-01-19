package cn.edu.zju.socialnetwork.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyPathConfiguration implements WebMvcConfigurer {

    @Autowired
    private Environment env;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/uploaded/**").addResourceLocations("file:" + env.getProperty("upload.path"));
        registry.addResourceHandler("/headpics/**").addResourceLocations("classpath:/static/headpics/");
        registry.addResourceHandler("/momentpics/**").addResourceLocations("classpath:/momentpics/");
    }

}
