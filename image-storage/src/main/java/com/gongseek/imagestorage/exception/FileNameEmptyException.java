package com.gongseek.imagestorage.exception;

public class FileNameEmptyException extends ImageFileIOException {

    public FileNameEmptyException() {
        super("이미지 파일명은 공백일 수 없습니다.");
    }
}
