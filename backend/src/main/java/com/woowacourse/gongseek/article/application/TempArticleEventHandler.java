package com.woowacourse.gongseek.article.application;

import com.woowacourse.gongseek.article.application.dto.TempArticleEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class TempArticleEventHandler {

    private final TempArticleEventService tempArticleEventService;

    @Async
    @TransactionalEventListener
    public void deleteTempArticle(TempArticleEvent event) {
        tempArticleEventService.delete(event.getTempArticleId());
    }
}
