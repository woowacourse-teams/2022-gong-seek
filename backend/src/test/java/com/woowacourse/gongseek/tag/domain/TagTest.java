package com.woowacourse.gongseek.tag.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongseek.tag.exception.TagNameLengthException;
import com.woowacourse.gongseek.tag.exception.TagNameNullOrBlankException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("NonAsciiCharacters")
class TagTest {

    @Test
    void 태그를_생성한다() {
        Tag tag = new Tag("Spring");

        assertThat(tag.getName().getValue()).isEqualTo("SPRING");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void 태그의_이름이_null_이거나_빈값이면_예외가_발생한다(String name) {
        assertThatThrownBy(() -> new Tag(name))
                .isInstanceOf(TagNameNullOrBlankException.class)
                .hasMessage("해시태그 이름은 비어있을 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 20})
    void 태그의_이름은_2자_이상_20자_이하이다(int length) {
        String name = "A".repeat(length);
        Tag tag = new Tag(name);

        assertThat(tag.getName().getValue()).isEqualTo(name);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 21})
    void 태그의_이름이_2자_미만_20자_초과이면_예외가_발생한다(int length) {
        String name = "A".repeat(length);
        assertThatThrownBy(() -> new Tag(name))
                .isInstanceOf(TagNameLengthException.class)
                .hasMessage("해시태그 이름은 2자 이상 20자 이하입니다.");
    }
}
