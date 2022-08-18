package com.gongseek.imagestorage.application;

import com.gongseek.imagestorage.application.dto.ImageUrlResponse;
import com.gongseek.imagestorage.domain.UploadImageFile;
import com.gongseek.imagestorage.exception.ImageUploadFailException;
import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    private final String directory;
    private final String serverPath;

    public ImageService(@Value("${file.image-dir}") String directory,
                        @Value("${file.server-path}") String serverPath) {
        this.directory = directory;
        this.serverPath = serverPath;
    }

    public ImageUrlResponse upload(MultipartFile inputImageFile) {
        try {
            String imageFullPath = getFullPath(inputImageFile);
            inputImageFile.transferTo(new File(imageFullPath));
            return new ImageUrlResponse(serverPath + imageFullPath);
        } catch (IOException e) {
            throw new ImageUploadFailException();
        }
    }

    private String getFullPath(MultipartFile inputImageFile) {
        UploadImageFile uploadImageFile = UploadImageFile.from(inputImageFile);
        return directory + uploadImageFile.getStoredFileName();
    }
}
