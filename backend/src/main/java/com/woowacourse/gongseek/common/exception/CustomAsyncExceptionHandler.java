package com.woowacourse.gongseek.common.exception;

import java.lang.reflect.Method;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

@Slf4j
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... params) {
        log.warn("비동기 처리에서 예외가 발생했습니다."
                + "예외 메세지 : " + throwable.getMessage()
                + "메서드 이름 : " + method.getName()
                + "파라미터 : " + Arrays.toString(params));
    }
}
