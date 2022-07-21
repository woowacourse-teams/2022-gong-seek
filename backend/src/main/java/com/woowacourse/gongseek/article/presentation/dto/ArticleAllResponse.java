package com.woowacourse.gongseek.article.presentation.dto;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArticleAllResponse {

    private Long id;
    private String title;
    private AuthorDto author;
    private String content;
    private String category;
    private Integer commentCount;
    private LocalDateTime createdAt;

    public static ArticleAllResponse of(Article article, Integer commentCount) {
        return new ArticleAllResponse(
                article.getId(),
                article.getTitle(),
                new AuthorDto(article.getMember()),
                article.getContent(),
                article.getCategory().getValue(),
                commentCount,
                article.getCreatedAt());
    }
}
