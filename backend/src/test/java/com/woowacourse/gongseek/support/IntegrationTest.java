package com.woowacourse.gongseek.support;


import com.woowacourse.gongseek.auth.application.OAuthClient;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(RollbackExtension.class)
@SpringBootTest
public abstract class IntegrationTest {

    @MockBean
    protected OAuthClient githubOAuthClient;
}

