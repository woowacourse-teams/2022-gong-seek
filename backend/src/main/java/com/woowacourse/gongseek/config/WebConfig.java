package com.woowacourse.gongseek.config;

import ch.qos.logback.access.servlet.TeeFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String ALLOWED_METHOD_NAMES = "GET,POST,PUT,DELETE,OPTIONS,PATCH";

    @Value("${cors.url.service}")
    private String serviceUrl;

    @Value("${cors.url.local}")
    private String localUrl;

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(serviceUrl, localUrl)
                .allowCredentials(true)
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
                .exposedHeaders("*");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public FilterRegistrationBean<TeeFilter> requestLoggingFilter() {
        return new FilterRegistrationBean<>(new TeeFilter());
    }
}
