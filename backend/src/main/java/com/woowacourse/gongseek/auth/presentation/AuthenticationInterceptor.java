package com.woowacourse.gongseek.auth.presentation;

import static org.hibernate.validator.internal.metadata.core.ConstraintHelper.PAYLOAD;

import com.woowacourse.gongseek.auth.exception.InvalidTokenException;
import com.woowacourse.gongseek.auth.infra.JwtTokenProvider;
import com.woowacourse.gongseek.auth.utils.TokenExtractor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String GUEST_ACCESS_TOKEN = "Bearer null";

    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        if (isGuest(request)) {
            return true;
        }

        String token = TokenExtractor.extract(request.getHeader(HttpHeaders.AUTHORIZATION));
        validateToken(token);
        String payload = jwtTokenProvider.getPayload(token);
        request.setAttribute(PAYLOAD, payload);
        return true;
    }

    private boolean isGuest(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION).equals(GUEST_ACCESS_TOKEN);
    }

    private void validateToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }
    }
}
