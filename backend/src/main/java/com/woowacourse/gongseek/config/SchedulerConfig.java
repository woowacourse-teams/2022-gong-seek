package com.woowacourse.gongseek.config;

import com.woowacourse.gongseek.article.application.ArticleService;
import com.woowacourse.gongseek.article.domain.scheduler.CountScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    private final ArticleService articleService;

    public SchedulerConfig(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Bean
    public CountScheduler countScheduler() {
        return new CountScheduler(articleService);
    }
}
