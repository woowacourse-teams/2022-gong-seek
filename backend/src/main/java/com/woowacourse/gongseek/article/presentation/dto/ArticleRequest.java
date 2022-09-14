package com.woowacourse.gongseek.article.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.Content;
import com.woowacourse.gongseek.article.domain.Title;
import com.woowacourse.gongseek.article.domain.Views;
import com.woowacourse.gongseek.article.domain.articletag.ArticleTags;
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
public class ArticleRequest {

    @Length(max = 500)
    private String title;

    @Length(max = 10_000)
    private String content;

    @NotBlank
    private String category;

    private List<String> tag;

    @NotNull
    @JsonProperty("isAnonymous")
    private Boolean isAnonymous;

    private Long tempArticleId;

    public ArticleRequest(String title, String content, String category, List<String> tag, Boolean isAnonymous) {
        this(title, content, category, tag, isAnonymous, null);
    }

    public Article toArticle(Member member) {
        return Article.builder()
                .title(new Title(title))
                .content(new Content(content))
                .category(Category.from(category))
                .member(member)
                .views(new Views())
                .articleTags(new ArticleTags())
                .isAnonymous(isAnonymous)
                .build();
    }
}
