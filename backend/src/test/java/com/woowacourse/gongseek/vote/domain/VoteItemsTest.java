package com.woowacourse.gongseek.vote.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.vote.exception.InvalidVoteItemCountException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("투표 항목 리스트")
class VoteItemsTest {

    @ParameterizedTest
    @MethodSource("getVoteItems")
    void 투표_항목의_수가_올바르지_않은_경우_예외를_발생한다(Set<VoteItem> items) {
        assertThatThrownBy(() -> new VoteItems(items))
                .isExactlyInstanceOf(InvalidVoteItemCountException.class)
                .hasMessage("투표 항목 수는 2이상 5이하여야 합니다. 현재 길이 : " + items.size());
    }

    static Stream<Arguments> getVoteItems() {
        Vote vote = new Vote(
                new Article("title", "content", Category.DISCUSSION, new Member("slow", "hanull", "avatarUrl"), false),
                LocalDateTime.now().plusDays(7)
        );
        return Stream.of(
                Arguments.of(Set.of(new VoteItem("item1", vote))),
                Arguments.of(Set.of(new VoteItem("item1", vote),
                                new VoteItem("item2", vote),
                                new VoteItem("item3", vote),
                                new VoteItem("item4", vote),
                                new VoteItem("item5", vote),
                                new VoteItem("item6", vote)
                        )
                )
        );
    }
}
