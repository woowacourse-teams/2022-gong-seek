package com.woowacourse.gongseek.article.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.gongseek.article.domain.ArticleTemp;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.member.domain.Member;
import java.util.List;
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
public class ArticleTempRequest {

    private Long id;

    @Length(max = 500)
    private String title;

    @Length(max = 10_000)
    private String content;

    @NotBlank
    private String category;

    private List<String> tags;

    @NotNull
    @JsonProperty("isAnonymous")
    private Boolean isAnonymous;

    public ArticleTempRequest(String title, String content, String category, List<String> tags, boolean isAnonymous) {
        this(null, title, content, category, tags, isAnonymous);
    }

    public ArticleTemp toEntity(Member member) {
        return new ArticleTemp(title, content, Category.from(category), member, tags, isAnonymous);
    }
}
