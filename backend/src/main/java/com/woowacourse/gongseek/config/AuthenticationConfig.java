package com.woowacourse.gongseek.config;

import com.woowacourse.gongseek.auth.infra.JwtTokenProvider;
import com.woowacourse.gongseek.auth.presentation.AuthenticationArgumentResolver;
import com.woowacourse.gongseek.auth.presentation.AuthenticationInterceptor;
import com.woowacourse.gongseek.auth.presentation.RefreshTokenInterceptor;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthenticationConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor(jwtTokenProvider))
                .addPathPatterns("/**")
                .excludePathPatterns("/docs/**")
                .excludePathPatterns("/api/auth/**");

        registry.addInterceptor(new RefreshTokenInterceptor(jwtTokenProvider))
                .addPathPatterns("/api/auth/refresh");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationArgumentResolver());
    }
}
