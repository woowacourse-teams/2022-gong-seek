package com.woowacourse.gongseek.image.application;

import com.woowacourse.gongseek.image.infra.S3Uploader;
import com.woowacourse.gongseek.image.presentation.dto.ImageUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final S3Uploader s3Uploader;

    public ImageUrlResponse upload(final MultipartFile uploadImageFile) {
        return new ImageUrlResponse(s3Uploader.upload(uploadImageFile));
    }
}
