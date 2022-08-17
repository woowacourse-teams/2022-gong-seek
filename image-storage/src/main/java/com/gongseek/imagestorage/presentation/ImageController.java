package com.gongseek.imagestorage.presentation;

import com.gongseek.imagestorage.application.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/api/images/upload")
    public ResponseEntity<String> uploadImage(@RequestPart MultipartFile imageFile) {
        return ResponseEntity.ok(imageService.upload(imageFile).toString());
    }
}
