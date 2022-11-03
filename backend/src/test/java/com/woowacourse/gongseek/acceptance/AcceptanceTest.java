package com.woowacourse.gongseek.acceptance;

import com.woowacourse.gongseek.support.DatabaseCleaner;
import com.woowacourse.gongseek.support.RedisContainerTest;
import com.woowacourse.gongseek.support.RollbackExtension;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(RollbackExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public abstract class AcceptanceTest extends RedisContainerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void init() {
        RestAssured.port = port;
    }
}
