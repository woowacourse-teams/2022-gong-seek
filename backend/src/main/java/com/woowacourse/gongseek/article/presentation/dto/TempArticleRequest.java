package com.woowacourse.gongseek.article.presentation.dto;

import com.woowacourse.gongseek.article.domain.TempArticle;
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
public class TempArticleRequest {

    private Long id;

    @Length(max = 500)
    private String title;

    @Length(max = 10_000)
    private String content;

    @NotBlank
    private String category;

    private List<String> tags;

    private boolean isAnonymous;

    public TempArticleRequest(String title, String content, String category, List<String> tags, boolean isAnonymous) {
        this(null, title, content, category, tags, isAnonymous);
    }

    public TempArticle toEntity(Member member) {
        return new TempArticle(title, content, category, member, tags, isAnonymous);
    }
}
