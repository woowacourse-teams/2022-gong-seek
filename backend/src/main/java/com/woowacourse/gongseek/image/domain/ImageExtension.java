package com.woowacourse.gongseek.image.domain;

import java.util.Set;
import java.util.stream.Stream;

public enum ImageExtension {

    APNG(Set.of("apnp")),
    AVIF(Set.of("avif")),
    GIF(Set.of("gif")),
    JPEG(Set.of("jpg", "jpeg", "jfif", "pjpeg", "pjp")),
    PNG(Set.of("png")),
    SVG(Set.of("svg")),
    WEBP(Set.of("webp")),
    ;

    private final Set<String> values;

    ImageExtension(final Set<String> values) {
        this.values = values;
    }

    public static boolean isSupport(final String extension) {
        return Stream.of(values())
                .anyMatch(it -> it.values.contains(extension.toLowerCase()));
    }
}
