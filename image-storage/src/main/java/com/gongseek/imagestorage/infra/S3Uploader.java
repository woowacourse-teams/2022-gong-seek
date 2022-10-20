package com.gongseek.imagestorage.infra;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.gongseek.imagestorage.domain.ImageFile;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3 amazonS3;

    @Value("${application.bucket.name}")
    private String bucket;

    @Value("${application.cloudfront.url}")
    private String cloudFrontUrl;

    public String upload(final MultipartFile uploadImageFile) {
        final ImageFile imageFile = ImageFile.from(uploadImageFile);
        try {
            amazonS3.putObject(bucket, imageFile.getOriginFileName().getValue(), uploadImageFile.getInputStream(),
                    createObjectMetadata(uploadImageFile));
            return cloudFrontUrl + imageFile.getOriginFileName().getValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ObjectMetadata createObjectMetadata(final MultipartFile uploadImageFile) {
        final ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(uploadImageFile.getContentType());
        objectMetadata.setContentLength(uploadImageFile.getSize());
        objectMetadata.setCacheControl("max-age=31536000");
        return objectMetadata;
    }
}
