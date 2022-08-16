package com.woowacourse.gongseek.tag.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongseek.article.exception.DuplicateTagException;
import com.woowacourse.gongseek.tag.exception.ExceededTagSizeException;
import java.util.List;
import org.junit.jupiter.api.Test;

class TagsTest {

    @Test
    void 해시태그는_중복되어서는_안된다() {
        assertThatThrownBy(() -> new Tags(List.of(new Tag("Spring"), new Tag("spring"))))
                .isInstanceOf(DuplicateTagException.class)
                .hasMessage("해시태그 이름은 중복될 수 없습니다.");
    }

    @Test
    void 해시태그는_5개를_넘을_수_없다() {
        assertThatThrownBy(() -> new Tags(
                List.of(new Tag("Spring"), new Tag("java"), new Tag("c++"),
                        new Tag("react"), new Tag("backend"), new Tag("infra"))
        ))
                .isInstanceOf(ExceededTagSizeException.class)
                .hasMessage("해시태그는 한 게시글 당 최대 5개입니다.");
    }
}
