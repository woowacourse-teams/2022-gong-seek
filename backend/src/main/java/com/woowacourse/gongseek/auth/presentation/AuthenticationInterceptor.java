package com.woowacourse.gongseek.auth.presentation;

import com.woowacourse.gongseek.auth.application.JwtTokenProvider;
import com.woowacourse.gongseek.auth.utils.TokenExtractor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String PAYLOAD = "payload";

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        String token = TokenExtractor.extract(request);
        validateToken(token);
        request.setAttribute(PAYLOAD, jwtTokenProvider.getPayload(token));
        return true;
    }

    private void validateToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }
}
