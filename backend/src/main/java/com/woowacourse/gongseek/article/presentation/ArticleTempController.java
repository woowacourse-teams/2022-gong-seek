package com.woowacourse.gongseek.article.presentation;

import com.woowacourse.gongseek.article.application.ArticleTempService;
import com.woowacourse.gongseek.article.presentation.dto.ArticleTempIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleTempRequest;
import com.woowacourse.gongseek.auth.presentation.AuthenticationPrinciple;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/temp-articles")
@RestController
public class ArticleTempController {

    private final ArticleTempService articleTempService;

    @PostMapping
    public ResponseEntity<ArticleTempIdResponse> create(
            @AuthenticationPrinciple AppMember appMember,
            @Valid @RequestBody ArticleTempRequest articleTempRequest
    ) {
        final ArticleTempIdResponse articleTempIdResponse = articleTempService.createOrUpdate(appMember, articleTempRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(articleTempIdResponse);
    }
}
