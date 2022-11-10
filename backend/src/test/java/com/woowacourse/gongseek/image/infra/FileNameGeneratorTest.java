package com.woowacourse.gongseek.image.infra;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.woowacourse.gongseek.image.exception.UnsupportedImageExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FileNameGeneratorTest {

    private static final FileNameGenerator fileNameGenerator = new FileNameGenerator();

    @Test
    void 지원하지_않는_확장자인_경우_예외를_발생한다() {
        assertThatThrownBy(() -> fileNameGenerator.generate("name.fail"))
                .isInstanceOf(UnsupportedImageExtension.class)
                .hasMessageContaining("지원하지 않는 확장자 입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"apnp", "avif", "gif", "jpg", "jpeg", "jfif", "pjpeg", "pjp", "png", "svg", "webp"})
    void 지원하는_확장자인_경우_정상적으로_이름을_생성한다(final String extension) {
        assertThat(fileNameGenerator.generate("name" + "." + extension)).isNotNull();
    }
}
