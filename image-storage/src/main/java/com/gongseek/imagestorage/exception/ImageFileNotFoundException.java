package com.gongseek.imagestorage.exception;

public class ImageFileNotFoundException extends ImageFileIOException {

    public ImageFileNotFoundException() {
        super("이미지 파일이 존재하지 않습니다.");
    }
}
