package com.woowacourse.gongseek.image.application;

import com.woowacourse.gongseek.image.application.dto.ImageUrlResponse;
import com.woowacourse.gongseek.image.infra.FileNameGenerator;
import com.woowacourse.gongseek.image.infra.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final S3Uploader s3Uploader;
    private final FileNameGenerator fileNameGenerator;

    public ImageUrlResponse upload(final MultipartFile uploadImageFile) {
        validate(uploadImageFile);
        final String fileName = fileNameGenerator.generate(uploadImageFile.getOriginalFilename());
        final String uploadUrl = s3Uploader.upload(uploadImageFile, fileName);
        return new ImageUrlResponse(uploadUrl);
    }

    private void validate(final MultipartFile uploadImageFile) {
        ImageFileValidator.checkEmptyFileName(uploadImageFile.getOriginalFilename());
        ImageFileValidator.checkImageType(uploadImageFile);
    }
}
