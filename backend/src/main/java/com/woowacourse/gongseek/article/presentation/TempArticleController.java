package com.woowacourse.gongseek.article.presentation;

import com.woowacourse.gongseek.article.application.TempArticleService;
import com.woowacourse.gongseek.article.presentation.dto.TempArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.TempArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.TempArticlesResponse;
import com.woowacourse.gongseek.auth.presentation.AuthenticationPrinciple;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/temp-articles")
@RestController
public class TempArticleController {

    private final TempArticleService tempArticleService;

    @PostMapping
    public ResponseEntity<TempArticleIdResponse> create(@AuthenticationPrinciple AppMember appMember,
                                                        @Valid @RequestBody TempArticleRequest tempArticleRequest) {
        final TempArticleIdResponse tempArticleIdResponse = tempArticleService.createOrUpdate(appMember,
                tempArticleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(tempArticleIdResponse);
    }

    @GetMapping
    public ResponseEntity<TempArticlesResponse> getAll(@AuthenticationPrinciple AppMember appMember) {
        return ResponseEntity.ok(tempArticleService.getAll(appMember));
    }
}
