package com.woowacourse.gongseek.article.application;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.repository.QuestionRepository;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final QuestionRepository questionRepository;

    public ArticleIdResponse save(ArticleRequest articleRequest) {
        Article article = questionRepository.save(articleRequest.toEntity());
        return new ArticleIdResponse(article);
    }
}
