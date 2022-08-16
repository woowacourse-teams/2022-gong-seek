package com.woowacourse.gongseek.tag.domain;

import com.woowacourse.gongseek.article.exception.DuplicateTagException;
import com.woowacourse.gongseek.tag.exception.ExceededTagsException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class Tags {

    private static final int MAX_TAGS_SIZE = 5;

    private final List<Tag> tags;

    public Tags(List<Tag> tags) {
        validateDuplicateTag(tags);
        validateLength(tags);
        this.tags = tags;
    }

    public static Tags from(List<String> tags) {
        return new Tags(tags.stream()
                .map(Tag::new)
                .collect(Collectors.toList()));
    }

    private void validateDuplicateTag(List<Tag> tags) {
        long tagCount = tags.stream()
                .map(tag -> tag.getName().getValue())
                .distinct()
                .count();

        if (tagCount != tags.size()) {
            throw new DuplicateTagException();
        }
    }

    private void validateLength(List<Tag> tags) {
        if (tags.size() > MAX_TAGS_SIZE) {
            throw new ExceededTagsException();
        }
    }

    public List<String> getTagNames() {
        return tags.stream()
                .map(tag -> tag.getName().getValue())
                .collect(Collectors.toList());
    }
}
