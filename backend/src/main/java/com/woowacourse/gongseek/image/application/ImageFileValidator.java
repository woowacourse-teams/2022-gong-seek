package com.woowacourse.gongseek.image.application;

import com.woowacourse.gongseek.image.exception.UnsupportedContentTypeException;
import java.io.IOException;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;


public class ImageFileValidator {

    private static final Tika tika = new Tika();

    public static void validateMimeType(final MultipartFile uploadImageFile) {
        try {
            final String mimeType = tika.detect(uploadImageFile.getInputStream());
            if (!mimeType.startsWith("image")) {
                throw new UnsupportedContentTypeException(mimeType);
            }
        } catch (IOException e) {
            throw new UnsupportedContentTypeException();
        }
    }
}
