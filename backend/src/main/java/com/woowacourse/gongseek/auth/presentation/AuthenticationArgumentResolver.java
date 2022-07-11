package com.woowacourse.gongseek.auth.presentation;

import com.woowacourse.gongseek.auth.presentation.dto.LoginMember;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String PAYLOAD = "payload";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrinciple.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        validateNullRequest(request);
        validateNullPayload(request);
        Long payload = Long.valueOf(String.valueOf(request.getAttribute(PAYLOAD)));
        return new LoginMember(payload);
    }

    private void validateNullRequest(HttpServletRequest request) {
        if (Objects.isNull(request)) {
            throw new IllegalArgumentException("요청 데이터가 없습니다.");
        }
    }

    private void validateNullPayload(HttpServletRequest request) {
        if (Objects.isNull(request.getAttribute(PAYLOAD))) {
            throw new IllegalArgumentException("요청 데이터에 payload가 존재하지 않습니다.");
        }
    }
}
