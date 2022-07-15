package com.woowacourse.gongseek.comment.presentation;

import com.woowacourse.gongseek.auth.presentation.AuthenticationPrinciple;
import com.woowacourse.gongseek.auth.presentation.dto.LoginMember;
import com.woowacourse.gongseek.comment.application.CommentService;
import com.woowacourse.gongseek.comment.presentation.dto.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/articles/{articleId}/comments")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> create(
            @AuthenticationPrinciple LoginMember loginMember,
            @PathVariable Long articleId,
            @RequestBody CommentRequest commentRequest) {
        commentService.save(loginMember, articleId, commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
