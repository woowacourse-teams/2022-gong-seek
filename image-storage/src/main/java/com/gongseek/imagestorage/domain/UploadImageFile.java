package com.gongseek.imagestorage.domain;

import com.gongseek.imagestorage.exception.FileNameEmptyException;
import com.gongseek.imagestorage.exception.ImageFileNotFoundException;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UploadImageFile {

    private final String originalFileName;
    private final String storedFileName;

    private UploadImageFile(String originalFileName, String storedFileName) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
    }

    public static UploadImageFile from(MultipartFile imageFile) {
        validateEmptyFile(imageFile);
        validateEmptyFileName(imageFile);

        String originalFilename = StringUtils.cleanPath(imageFile.getOriginalFilename());
        String storeFileName = createStoreFileName(originalFilename);

        return new UploadImageFile(originalFilename, storeFileName);
    }

    private static void validateEmptyFile(MultipartFile imageFile) {
        if (imageFile.isEmpty()) {
            throw new ImageFileNotFoundException();
        }
    }

    private static void validateEmptyFileName(MultipartFile imageFile) {
        if (Objects.isNull(imageFile.getOriginalFilename())) {
            throw new FileNameEmptyException();
        }
    }

    private static String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String imageType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        return uuid + "." + imageType;
    }
}
