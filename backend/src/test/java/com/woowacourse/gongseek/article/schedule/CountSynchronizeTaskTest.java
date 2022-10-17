package com.woowacourse.gongseek.article.schedule;

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
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
public class CountSynchronizeTaskTest extends IntegrationTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleService articleService;

    private TriggerTask triggerTask;

    private Member member;

    private Article article;

    @Transactional
    @BeforeEach
    void setUp() {
        member = memberRepository.save(new Member("rennon", "brorae", "avatar.com"));
        article = articleRepository.save(new Article("title", "content", Category.DISCUSSION, member, false));
        article.updateLikeCountAndCommentCount(10L, 10L);

        triggerTask = new CountSynchronizeTask(articleService);
    }

    @ParameterizedTest
    @MethodSource("provideCurrentTimeAndExecuteTime")
    void 좋아요수와_댓글수를_동기화한다(LocalDateTime current, LocalDateTime expectedExecuteTime) {
        Trigger trigger = triggerTask.getTrigger();

        Date date = Date.from(current.toInstant(ZoneOffset.of("+9")));
        LocalDateTime actualNextExecuteTime = trigger
                .nextExecutionTime(new SimpleTriggerContext(date, date, date))
                .toInstant().atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime();

        Article article = articleRepository.findById(this.article.getId()).get();

        assertAll(
                () -> assertThat(actualNextExecuteTime).isEqualTo(expectedExecuteTime),
                () -> assertThat(article.getLikeCount()).isEqualTo(0),
                () -> assertThat(article.getCommentCount()).isEqualTo(0)
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
