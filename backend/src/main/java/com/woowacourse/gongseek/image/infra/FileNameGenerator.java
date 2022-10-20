package com.woowacourse.gongseek.image.infra;

import com.woowacourse.gongseek.image.domain.FileExtension;
import com.woowacourse.gongseek.image.exception.UnsupportedFilExtensionException;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;

public class FileNameGenerator {

    public String generate(final String originalFilename) {
        final String fileName = createNewName();
        final String extension = getExtension(originalFilename);
        return fileName + "." + extension;
    }

    private static String getExtension(final String originalFilename) {
        final String extension = FilenameUtils.getExtension(originalFilename);
        if (FileExtension.isSupport(extension)) {
            return extension;
        }
        throw new UnsupportedFilExtensionException(extension);
    }

    private static String createNewName() {
        return UUID.randomUUID().toString();
    }
}
