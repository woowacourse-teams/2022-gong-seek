package com.woowacourse.gongseek.article.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.tag.domain.Tag;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArticleRequest {

    @Length(max = 500)
    private String title;

    @Length(max = 10_000)
    private String content;

    @NotBlank
    private String category;

    private List<String> hashtag;

    @NotNull
    @JsonProperty("isAnonymous")
    private Boolean isAnonymous;

    public Article toEntity(Member member) {
        return new Article(title, content, Category.from(category), member, isAnonymous);
    }

    public List<Tag> toTags() {
        return hashtag.stream()
                .map(Tag::new)
                .collect(Collectors.toList());
    }
}
