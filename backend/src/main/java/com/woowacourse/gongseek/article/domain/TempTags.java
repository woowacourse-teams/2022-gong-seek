package com.woowacourse.gongseek.article.domain;

import com.woowacourse.gongseek.article.exception.DuplicateTagException;
import com.woowacourse.gongseek.tag.exception.ExceededTagSizeException;
import com.woowacourse.gongseek.tag.exception.TagNameLengthException;
import com.woowacourse.gongseek.tag.exception.TagNameNullOrBlankException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class TempTags {

    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 20;
    private static final int MAX_TAGS_COUNT = 5;
    private static final String TEMP_TAG_NAME_DELIMITER = "&";

    @Column(name = "temp_tags")
    private String values;

    public TempTags(final List<String> values) {
        validateCount(values.size());
        validateName(values);
        validateDuplicate(values);
        this.values = joinTags(values);
    }

    private void validateCount(final int tagCount) {
        if (tagCount > MAX_TAGS_COUNT) {
            throw new ExceededTagSizeException();
        }
    }

    private void validateName(final List<String> values) {
        for (String value : values) {
            checkNullOrBlank(value);
            checkLengthIsInRange(value.length());
        }
    }

    private void checkNullOrBlank(final String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new TagNameNullOrBlankException();
        }
    }

    private void checkLengthIsInRange(final int valueLength) {
        if (valueLength < MIN_NAME_LENGTH || valueLength > MAX_NAME_LENGTH) {
            throw new TagNameLengthException();
        }
    }

    private void validateDuplicate(final List<String> values) {
        if (values.size() != new HashSet<>(values).size()) {
            throw new DuplicateTagException();
        }
    }

    private String joinTags(final List<String> values) {
        StringJoiner joiner = new StringJoiner(TEMP_TAG_NAME_DELIMITER);
        for (String value : values) {
            joiner.add(value);
        }
        return joiner.toString();
    }

    public List<String> toResponse() {
        return Arrays.asList(values.split(TEMP_TAG_NAME_DELIMITER));
    }
}