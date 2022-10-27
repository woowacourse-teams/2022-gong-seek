package com.woowacourse.gongseek.member.presentation.dto;

import com.woowacourse.gongseek.article.domain.Article;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MyPageArticlesResponse {

    private List<MyPageArticleResponse> articles;

    public static MyPageArticlesResponse of(List<Article> articles, List<Long> commentCounts) {
        List<MyPageArticleResponse> myPageArticleResponses = new ArrayList<>();
        for (int i = 0; i < articles.size(); i++) {
            myPageArticleResponses.add(new MyPageArticleResponse(articles.get(i), commentCounts.get(i)));
        }
        return new MyPageArticlesResponse(myPageArticleResponses);
    }
}
