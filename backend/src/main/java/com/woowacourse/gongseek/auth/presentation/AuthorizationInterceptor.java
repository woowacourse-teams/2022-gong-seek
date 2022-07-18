package com.woowacourse.gongseek.auth.presentation;

import static org.hibernate.validator.internal.metadata.core.ConstraintHelper.PAYLOAD;

import com.woowacourse.gongseek.auth.infra.JwtTokenProvider;
import com.woowacourse.gongseek.auth.utils.TokenExtractor;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthorizationInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthorizationInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (Objects.isNull(request.getHeader(HttpHeaders.AUTHORIZATION))) {
            return true;
        }

        String token = TokenExtractor.extract(request.getHeader(HttpHeaders.AUTHORIZATION));
        if (jwtTokenProvider.validateToken(token)) {
            request.setAttribute(PAYLOAD, jwtTokenProvider.getPayload(token));
            return true;
        }
        return true;
    }
}
