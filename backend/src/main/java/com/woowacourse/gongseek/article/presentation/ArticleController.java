package com.woowacourse.gongseek.article.presentation;

import com.woowacourse.gongseek.article.application.ArticleService;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticlePageResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateResponse;
import com.woowacourse.gongseek.auth.presentation.AuthenticationPrinciple;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
            @AuthenticationPrinciple AppMember appMember,
            @Valid @RequestBody ArticleRequest articleRequest
    ) {
        ArticleIdResponse articleIdResponse = articleService.save(appMember, articleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(articleIdResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getOne(@AuthenticationPrinciple AppMember appMember, @PathVariable Long id) {
        return ResponseEntity.ok(articleService.getOne(appMember, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleUpdateResponse> update(
            @AuthenticationPrinciple AppMember appMember,
            @Valid @RequestBody ArticleUpdateRequest articleUpdateRequest,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(articleService.update(appMember, articleUpdateRequest, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@AuthenticationPrinciple AppMember appMember, @PathVariable Long id) {
        articleService.delete(appMember, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ArticlePageResponse> getAll(
            @RequestParam String category,
            @RequestParam String sort,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(required = false) Integer cursorViews,
            @RequestParam Integer pageSize,
            @AuthenticationPrinciple AppMember appMember
    ) {
        ArticlePageResponse response = articleService.getAll(cursorId, cursorViews, category, sort, pageSize,
                appMember);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ArticlePageResponse> search(
            @RequestParam(required = false) Long cursorId,
            @RequestParam Integer pageSize,
            @RequestParam String searchText,
            @AuthenticationPrinciple AppMember appMember
    ) {
        ArticlePageResponse response = articleService.search(cursorId, pageSize, searchText, appMember);
        return ResponseEntity.ok(response);
    }
}
