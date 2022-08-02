package com.woowacourse.gongseek.comment.presentation.dto;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.comment.domain.Comment;
import com.woowacourse.gongseek.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommentRequest {

    @Length(max = 10_000)
    private String content;

    private Boolean isAnonymous;

    public Comment toEntity(Member member, Article article) {
        return new Comment(content, member, article, isAnonymous);
    }
}
