package com.woowacourse.gongseek.like.schedule;

import com.woowacourse.gongseek.article.application.ArticleService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CountSynchronizeScheduler extends TriggerTask {

    public CountSynchronizeScheduler(ArticleService articleService) {
        super(runnable(articleService), new CronTrigger("0 0 4 * * *", ZoneId.of("Asia/Seoul")));
    }

    private static Runnable runnable(ArticleService articleService) {
        log.info("{} CountSynchronizeTask work !", LocalDateTime.now());
        return articleService::synchronizeLikeCountAndCommentCount;
    }
}
