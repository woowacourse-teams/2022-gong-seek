package com.woowacourse.gongseek.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongseek.member.exception.NameNullOrEmptyException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class NameTest {

    @ParameterizedTest
    @ValueSource(ints = {2, 20})
    void 이름을_1글자_이상으로_생성이_가능하다(int length) {
        String value = "A".repeat(length);
        Name name = new Name(value);

        assertThat(name.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", " ", "   "})
    void 이름이_null_이거나_빈값이면_예외가_발생한다(String value) {
        assertThatThrownBy(() -> new Name(value))
                .isInstanceOf(NameNullOrEmptyException.class)
                .hasMessage("회원의 이름은 1자 이상이어야 합니다.");
    }
}
