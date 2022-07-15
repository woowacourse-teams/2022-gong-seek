package com.woowacourse.gongseek.auth.presentation;

import static org.hibernate.validator.internal.metadata.core.ConstraintHelper.PAYLOAD;

import com.woowacourse.gongseek.auth.presentation.dto.SearchMember;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrinciple.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        validateNullRequest(request);
        return getSearchMember(request);
    }

    private SearchMember getSearchMember(HttpServletRequest request) {
        if (Objects.isNull(request.getAttribute(PAYLOAD))) {
            new SearchMember(0L, true);
        }
        Long payload = Long.valueOf(String.valueOf(request.getAttribute(PAYLOAD)));
        return new SearchMember(payload, false);
    }

    private void validateNullRequest(HttpServletRequest request) {
        if (Objects.isNull(request)) {
            throw new IllegalArgumentException("요청 데이터가 없습니다.");
        }
    }
}
