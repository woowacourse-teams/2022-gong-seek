package com.gongseek.imagestorage.application.dto;

import lombok.Getter;

@Getter
public class ImageUrlResponse {

    private final String imageUrl;

    public ImageUrlResponse(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
