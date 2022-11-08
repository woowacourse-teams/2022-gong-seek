package com.woowacourse.gongseek.image.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.woowacourse.gongseek.image.exception.UnsupportedImageFileTypeException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;
import org.apache.tika.Tika;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

class ImageFileValidatorTest {

    private static final Tika tika = new Tika();
    private static final ClassLoader classLoader = ImageFileValidator.class.getClassLoader();

    @Test
    void 업로드_파일의_mime_type이_image가_아니라면_예외를_발생한다() throws IOException {
        final String failImageName = "failimage";
        final URL resource = classLoader.getResource(failImageName + ".jpg");
        final File file = new File(Objects.requireNonNull(resource).getFile());
        final MockMultipartFile multipartFile = new MockMultipartFile("image", failImageName, tika.detect(file),
                Files.readAllBytes(file.toPath()));

        assertThatThrownBy(() -> ImageFileValidator.checkImageType(multipartFile))
                .isInstanceOf(UnsupportedImageFileTypeException.class);
    }

    @Test
    void 업로드_파일의_mime_type이_image면_예외를_발생하지_않는다() throws IOException {
        final String successImageName = "successimage";
        final URL resource = classLoader.getResource(successImageName + ".jpg");
        final File file = new File(Objects.requireNonNull(resource).getFile());
        final MockMultipartFile multipartFile = new MockMultipartFile("image", successImageName, tika.detect(file),
                Files.readAllBytes(file.toPath()));

        assertDoesNotThrow(() -> ImageFileValidator.checkImageType(multipartFile));
    }
}
