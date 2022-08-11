package com.woowacourse.gongseek.like.presentation;

import com.woowacourse.gongseek.auth.presentation.AuthenticationPrinciple;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import com.woowacourse.gongseek.like.application.LikeService;
import com.woowacourse.gongseek.like.presentation.dto.LikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/articles/{articleId}/like")
@RestController
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<LikeResponse> likeArticle(
            @PathVariable Long articleId,
            @AuthenticationPrinciple AppMember appMember
    ) {
        LikeResponse response = likeService.likeArticle(appMember, articleId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping
    public ResponseEntity<LikeResponse> unlikeArticle(
            @PathVariable Long articleId,
            @AuthenticationPrinciple AppMember appMember
    ) {
        LikeResponse response = likeService.unlikeArticle(appMember, articleId);
        return ResponseEntity.ok(response);
    }
}
