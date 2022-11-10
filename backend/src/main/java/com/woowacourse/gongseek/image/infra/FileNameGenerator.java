package com.woowacourse.gongseek.image.infra;

import com.woowacourse.gongseek.image.domain.ImageExtension;
import com.woowacourse.gongseek.image.exception.UnsupportedImageExtension;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

@Component
public class FileNameGenerator {

    public String generate(final String originalFilename) {
        final String fileName = createNewName();
        final String extension = getExtension(originalFilename);
        return fileName + "." + extension;
    }

    private String createNewName() {
        return UUID.randomUUID().toString();
    }

    private String getExtension(final String originalFilename) {
        final String extension = FilenameUtils.getExtension(originalFilename);
        if (!ImageExtension.isSupport(extension)) {
            throw new UnsupportedImageExtension(extension);
        }
        return extension;
    }
}
