package com.woowacourse.gongseek.article.presentation;

import com.woowacourse.gongseek.article.application.ArticleService;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/articles")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleIdResponse> create(@RequestBody ArticleRequest articleRequest) {
        ArticleIdResponse articleIdResponse = articleService.save(articleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(articleIdResponse);
    }
}
