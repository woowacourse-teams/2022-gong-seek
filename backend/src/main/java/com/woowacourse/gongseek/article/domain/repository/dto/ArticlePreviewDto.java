package com.woowacourse.gongseek.article.domain.repository.dto;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ArticlePreviewDto {

    private Long id;
    private String title;
    private List<String> tag;
    private AuthorDto author;
    private String content;
    private String category;
    private Long views;
    private Long commentCount;
    private Long likeCount;
    private Boolean isLike;
    private LocalDateTime createdAt;

    public ArticlePreviewDto(Long id,
                             String title,
                             Member member,
                             String content,
                             Category category,
                             Boolean isAnonymous,
                             Long views,
                             Long commentCount,
                             Long likeCount,
                             LocalDateTime createdAt) {
        this(
                id,
                title,
                null,
                new AuthorDto(member.getMemberOrAnonymous(isAnonymous)),
                content,
                category.getValue(),
                views,
                commentCount,
                likeCount,
                null,
                createdAt
        );
    }

    public void setTagName(List<String> tagNames) {
        this.tag = tagNames;
    }

    public void setIsLike(Boolean isLike) {
        this.isLike = isLike;
    }
}
