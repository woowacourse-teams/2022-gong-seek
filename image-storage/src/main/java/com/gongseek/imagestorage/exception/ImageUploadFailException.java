package com.gongseek.imagestorage.exception;

public class ImageUploadFailException extends ImageFileIOException {

    public ImageUploadFailException() {
        super("이미지 파일 업로드를 실패했습니다.");
    }
}
