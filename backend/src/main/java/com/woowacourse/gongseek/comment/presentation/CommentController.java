package com.woowacourse.gongseek.comment.presentation;

import com.woowacourse.gongseek.auth.presentation.AuthenticationPrinciple;
import com.woowacourse.gongseek.auth.presentation.dto.LoginMember;
import com.woowacourse.gongseek.comment.application.CommentService;
import com.woowacourse.gongseek.comment.presentation.dto.CommentRequest;
import com.woowacourse.gongseek.comment.presentation.dto.CommentResponse;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/articles")
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{articleId}/comments")
    public ResponseEntity<Void> create(
            @AuthenticationPrinciple LoginMember loginMember,
            @PathVariable Long articleId,
            @RequestBody CommentRequest commentRequest) {
        commentService.save(loginMember, articleId, commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{articleId}/comments")
    public ResponseEntity<List<CommentResponse>> findByArticleId(
            @AuthenticationPrinciple LoginMember loginMember,
            @PathVariable Long articleId
    ) {
        return ResponseEntity.ok(commentService.findByArticleId(loginMember, articleId));
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Void> update(
            @AuthenticationPrinciple LoginMember loginMember,
            @PathVariable Long commentId,
            @RequestBody CommentRequest commentRequest) {
        commentService.update(loginMember, commentId, commentRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrinciple LoginMember loginMember,
            @PathVariable Long commentId
    ) {
        commentService.delete(loginMember, commentId);
        return ResponseEntity.ok().build();
    }
}
