package com.woowacourse.gongseek.image.application;

import com.woowacourse.gongseek.image.application.dto.ImageUrlResponse;
import com.woowacourse.gongseek.image.exception.FileNameEmptyException;
import com.woowacourse.gongseek.image.exception.UnsupportedContentTypeException;
import com.woowacourse.gongseek.image.infra.FileNameGenerator;
import com.woowacourse.gongseek.image.infra.S3Uploader;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final S3Uploader s3Uploader;
    private final FileNameGenerator fileNameGenerator;

    private static boolean isEmptyFileName(final MultipartFile uploadImageFile) {
        return Objects.requireNonNull(uploadImageFile.getOriginalFilename()).trim().isEmpty();
    }

    public ImageUrlResponse upload(final MultipartFile uploadImageFile) {
        validateFileName(uploadImageFile);
        validateContentType(uploadImageFile);
        final String fileName = fileNameGenerator.generate(uploadImageFile.getOriginalFilename());
        final String uploadUrl = s3Uploader.upload(uploadImageFile, fileName);
        return new ImageUrlResponse(uploadUrl);
    }

    private void validateFileName(final MultipartFile uploadImageFile) {
        if (isEmptyFileName(uploadImageFile)) {
            throw new FileNameEmptyException();
        }
    }

    private void validateContentType(final MultipartFile uploadImageFile) {
        final String contentType = uploadImageFile.getContentType();
        if (contentType == null || contentType.startsWith("image")) {
            throw new UnsupportedContentTypeException(contentType);
        }
    }
}
