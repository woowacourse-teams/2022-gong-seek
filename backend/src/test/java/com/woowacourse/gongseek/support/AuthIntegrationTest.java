package com.woowacourse.gongseek.support;

import com.woowacourse.gongseek.auth.application.OAuthClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public abstract class AuthIntegrationTest extends RedisContainerTest {

    @MockBean
    protected OAuthClient githubOAuthClient;
}
