package com.woowacourse.gongseek.article.domain;

import com.woowacourse.gongseek.article.exception.ArticleContentNullException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Content {

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String value;

    public Content(String value) {
        validateNull(value);
        this.value = value;
    }

    private void validateNull(String value) {
        if (Objects.isNull(value)) {
            throw new ArticleContentNullException();
        }
    }
}
