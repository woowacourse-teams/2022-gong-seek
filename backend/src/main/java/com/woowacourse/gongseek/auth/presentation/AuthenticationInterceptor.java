package com.woowacourse.gongseek.auth.presentation;

import static org.hibernate.validator.internal.metadata.core.ConstraintHelper.PAYLOAD;

import com.woowacourse.gongseek.auth.exception.InvalidAccessTokenException;
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

        String accessToken = TokenExtractor.extract(request.getHeader(HttpHeaders.AUTHORIZATION));
        validateAccessToken(accessToken);
        String payload = jwtTokenProvider.getAccessTokenPayload(accessToken);
        request.setAttribute(PAYLOAD, payload);
        return true;
    }

    private boolean isGuest(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION).equals(GUEST_ACCESS_TOKEN);
    }

    private void validateAccessToken(String accessToken) {
        if (!jwtTokenProvider.isValidAccessToken(accessToken)) {
            throw new InvalidAccessTokenException();
        }
    }
}
