package com.woowacourse.gongseek.article.presentation;

import com.woowacourse.gongseek.article.application.TempArticleService;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.TempArticleDetailResponse;
import com.woowacourse.gongseek.article.presentation.dto.TempArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.TempArticlesResponse;
import com.woowacourse.gongseek.auth.presentation.anntation.AuthenticationPrinciple;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
                                                        @Valid @RequestBody ArticleRequest tempArticleRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tempArticleService.createOrUpdate(appMember, tempArticleRequest));
    }

    @GetMapping
    public ResponseEntity<TempArticlesResponse> getAll(@AuthenticationPrinciple AppMember appMember) {
        return ResponseEntity.ok(tempArticleService.getAll(appMember));
    }

    @GetMapping("/{tempArticleId}")
    public ResponseEntity<TempArticleDetailResponse> getOne(@AuthenticationPrinciple AppMember appMember,
                                                            @PathVariable Long tempArticleId) {
        return ResponseEntity.ok(tempArticleService.getOne(appMember, tempArticleId));
    }

    @DeleteMapping("/{tempArticleId}")
    public ResponseEntity<Void> delete(@AuthenticationPrinciple AppMember appMember,
                                       @PathVariable Long tempArticleId) {
        tempArticleService.delete(tempArticleId, appMember);
        return ResponseEntity.noContent().build();
    }
}
