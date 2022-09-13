package com.woowacourse.gongseek.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public abstract class AcceptanceTest {

    @LocalServerPort
    private int port;
//
//    @Autowired
//    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void init() {
        RestAssured.port = port;
    }
//
//    @AfterEach
//    void tearDown() {
//        databaseCleaner.tableClear();
//    }
}
