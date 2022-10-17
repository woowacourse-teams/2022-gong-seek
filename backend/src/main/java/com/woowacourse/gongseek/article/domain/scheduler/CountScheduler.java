package com.woowacourse.gongseek.article.domain.scheduler;

import com.woowacourse.gongseek.article.application.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class CountScheduler {

    private final ArticleService articleService;

    @Scheduled(cron = "0 0 4 * * *")
    public void doSchedule() {
        articleService.synchronizeLikeCountAndCommentCount();
    }
}
