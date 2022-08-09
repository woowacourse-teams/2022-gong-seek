package com.woowacourse.gongseek.article.presentation.dto;

import com.woowacourse.gongseek.tag.domain.Tag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArticleUpdateRequest {

    @Length(max = 500)
    private String title;

    @Length(max = 10_000)
    private String content;

    private List<String> hashtag;

    public List<Tag> toTag() {
        return hashtag.stream()
                .map(Tag::new)
                .collect(Collectors.toList());
    }
}
