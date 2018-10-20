package com.opendoor.interview;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.opendoor.interview.web")
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    public WebConfig() {
        super();
    }
    
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //this will map uri to jsp view directly without a controller
        registry.addViewController("/hi")
                .setViewName("hello");
        registry.addViewController("/graph.html");
        registry.addViewController("/homepage.html");
    }

}