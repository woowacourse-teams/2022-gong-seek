package com.woowacourse.gongseek.image.domain;

import java.util.List;
import java.util.stream.Stream;

public enum ImageExtension {

    APNG(List.of("apnp")),
    AVIF(List.of("avif")),
    GIF(List.of("gif")),
    JPEG(List.of("jpg", "jpeg", "jfif", "pjpeg", "pjp")),
    PNG(List.of("png")),
    SVG(List.of("svg")),
    WEBP(List.of("webp")),
    ;

    private final List<String> values;

    ImageExtension(final List<String> values) {
        this.values = values;
    }

    public static boolean isSupport(final String extension) {
        return Stream.of(values())
                .anyMatch(it -> it.values.contains(extension));
    }
}
