package com.woowacourse.gongseek.article.presentation;

import com.woowacourse.gongseek.article.application.ArticleService;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleResponse;
import com.woowacourse.gongseek.auth.presentation.AuthenticationPrinciple;
import com.woowacourse.gongseek.auth.presentation.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/articles")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleIdResponse> create(
            @AuthenticationPrinciple User user,
            @RequestBody ArticleRequest articleRequest
    ) {
        ArticleIdResponse articleIdResponse = articleService.save(user, articleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(articleIdResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> findOne(
            @AuthenticationPrinciple User user,
            @PathVariable Long id,
            @RequestParam String category
    ) {
        return ResponseEntity.ok(articleService.findOne(user, id, category));
    }
}
