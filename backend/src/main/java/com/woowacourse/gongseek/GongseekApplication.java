package com.woowacourse.gongseek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class GongseekApplication {

    public static void main(String[] args) {
        SpringApplication.run(GongseekApplication.class, args);
    }

}
