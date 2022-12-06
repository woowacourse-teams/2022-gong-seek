package com.woowacourse.gongseek.image.application;

import com.woowacourse.gongseek.image.exception.FileNameEmptyException;
import com.woowacourse.gongseek.image.exception.UnsupportedImageFileTypeException;
import java.io.IOException;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

public class ImageFileValidator {

    private static final Tika tika = new Tika();

    public static void checkEmptyFileName(final String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new FileNameEmptyException();
        }
    }

    public static void checkImageType(final MultipartFile uploadImageFile) {
        try {
            final String mimeType = tika.detect(uploadImageFile.getInputStream());
            if (!mimeType.startsWith("image")) {
                throw new UnsupportedImageFileTypeException(mimeType);
            }
        } catch (IOException e) {
            throw new UnsupportedImageFileTypeException();
        }
    }
}
