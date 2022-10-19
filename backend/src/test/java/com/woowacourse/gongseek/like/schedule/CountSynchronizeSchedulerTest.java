package com.woowacourse.gongseek.like.schedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.application.ArticleService;
import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.support.IntegrationTest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.SimpleTriggerContext;

class CountSynchronizeSchedulerTest extends IntegrationTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;


    @ParameterizedTest
    @MethodSource("provideCurrentTimeAndExecuteTime")
    void 좋아요수와_댓글수를_동기화한다(LocalDateTime current, LocalDateTime expectedExecuteTime) {

        Member member = memberRepository.save(new Member("rennon", "brorae", "avatar.com"));
        Article article = articleRepository.save(new Article("title", "content", Category.DISCUSSION, member, false));
        article.updateLikeCountBatch(10L);

        Trigger trigger = new CountSynchronizeScheduler(articleService).getTrigger();

        Date date = Date.from(current.toInstant(ZoneOffset.of("+9")));
        LocalDateTime actualNextExecuteTime = Objects.requireNonNull(trigger
                        .nextExecutionTime(new SimpleTriggerContext(date, date, date)))
                .toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();

        Article findArticle = articleRepository.findById(article.getId()).get();

        assertAll(
                () -> assertThat(actualNextExecuteTime).isEqualTo(expectedExecuteTime),
                () -> assertThat(findArticle.getLikeCount()).isEqualTo(0),
                () -> assertThat(findArticle.getCommentCount()).isEqualTo(0)
        );
    }

    private static Stream<Arguments> provideCurrentTimeAndExecuteTime() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2022, 1, 1, 3, 59, 59),
                        LocalDateTime.of(2022, 1, 1, 4, 0, 0)),
                Arguments.of(LocalDateTime.of(2022, 12, 31, 4, 0, 1),
                        LocalDateTime.of(2023, 1, 1, 4, 0, 0))
        );
    }

}
