package com.woowacourse.gongseek.tag.application.dto;

import com.woowacourse.gongseek.tag.domain.Tag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TagsResponse {

    private List<String> tag;

    public static TagsResponse of(List<Tag> tags) {
        List<String> tagNames = tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
        return new TagsResponse(tagNames);
    }
}
