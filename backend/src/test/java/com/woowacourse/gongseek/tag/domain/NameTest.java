//package com.woowacourse.gongseek.tag.domain;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//
//import com.woowacourse.gongseek.tag.exception.TagNameLengthException;
//import com.woowacourse.gongseek.tag.exception.TagNameNullOrBlankException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.NullSource;
//import org.junit.jupiter.params.provider.ValueSource;
//
//class NameTest {
//
//    @Test
//    void 태그_이름은_대문자로_생성된다() {
//        Name name = new Name("Spring");
//
//        assertThat(name.getValue()).isEqualTo("SPRING");
//    }
//
//    @Test
//    void 태그_이름은_양끝_공백을_제거하고_생성된다() {
//        Name name = new Name("  Spring  ");
//
//        assertThat(name.getValue()).isEqualTo("SPRING");
//    }
//
//    @ParameterizedTest
//    @NullSource
//    @ValueSource(strings = {"", " ", "   "})
//    void 태그의_이름이_null_이거나_빈값이면_예외가_발생한다(String value) {
//        assertThatThrownBy(() -> new Name(value))
//                .isInstanceOf(TagNameNullOrBlankException.class)
//                .hasMessage("해시태그 이름은 비어있을 수 없습니다.");
//    }
//
//    @ParameterizedTest
//    @ValueSource(ints = {2, 20})
//    void 태그의_이름은_2자_이상_20자_이하이다(int length) {
//        String value = "A".repeat(length);
//        Name name = new Name(value);
//
//        assertThat(name.getValue()).isEqualTo(value);
//    }
//
//    @ParameterizedTest
//    @ValueSource(ints = {1, 21})
//    void 태그의_이름이_2자_미만_20자_초과이면_예외가_발생한다(int length) {
//        String value = "A".repeat(length);
//        assertThatThrownBy(() -> new Name(value))
//                .isInstanceOf(TagNameLengthException.class)
//                .hasMessage("해시태그 이름은 2자 이상 20자 이하입니다.");
//    }
//}
