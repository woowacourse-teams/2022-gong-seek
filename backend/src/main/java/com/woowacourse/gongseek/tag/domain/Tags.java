package com.woowacourse.gongseek.tag.domain;

import com.woowacourse.gongseek.article.exception.DuplicateTagException;
import com.woowacourse.gongseek.tag.exception.ExceededTagsException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class Tags {

    private List<Tag> tags;

    public Tags(List<Tag> tags) {
        validateDuplicateTag(tags);
        validateLength(tags);
        this.tags = tags;
    }

    private void validateDuplicateTag(List<Tag> tags) {
        long tagCount = tags.stream()
                .map(Tag::getName)
                .distinct()
                .count();

        if (tagCount != tags.size()) {
            throw new DuplicateTagException();
        }
    }

    private void validateLength(List<Tag> tags) {
        if (tags.size() > 5) {
            throw new ExceededTagsException();
        }
    }

    public List<String> getTagNames() {
        return tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
    }
}
