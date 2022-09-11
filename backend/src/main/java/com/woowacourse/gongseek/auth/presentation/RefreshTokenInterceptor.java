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

public class RefreshTokenInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public RefreshTokenInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        String accessToken = TokenExtractor.extract(request.getHeader(HttpHeaders.AUTHORIZATION));
        validateAccessToken(accessToken);
        String payload = jwtTokenProvider.getAccessTokenPayload(accessToken);
        request.setAttribute(PAYLOAD, payload);
        return true;
    }

    private void validateAccessToken(String accessToken) {
        if (!jwtTokenProvider.isValidOnlyClaims(accessToken)) {
            throw new InvalidAccessTokenException();
        }
    }
}
