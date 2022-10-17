package com.woowacourse.gongseek.auth.presentation;

import com.woowacourse.gongseek.auth.exception.NotMemberException;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
public class UserCheckAspect {

    private static final String GUEST_ACCESS_TOKEN = "Bearer null";

    @Before("@annotation(com.woowacourse.gongseek.auth.presentation.LoginUser)")
    public void checkLoginUser() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        if (isGuest(request)) {
            throw new NotMemberException();
        }
    }

    private boolean isGuest(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION).equals(GUEST_ACCESS_TOKEN);
    }
}
