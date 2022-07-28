package com.woowacourse.gongseek.comment.presentation;

import com.woowacourse.gongseek.auth.presentation.AuthenticationPrinciple;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import com.woowacourse.gongseek.comment.application.CommentService;
import com.woowacourse.gongseek.comment.presentation.dto.CommentRequest;
import com.woowacourse.gongseek.comment.presentation.dto.CommentsResponse;
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
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/articles")
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{articleId}/comments")
    public ResponseEntity<Void> create(
            @AuthenticationPrinciple AppMember appMember,
            @PathVariable Long articleId,
            @Valid @RequestBody CommentRequest commentRequest
    ) {
        commentService.save(appMember, articleId, commentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{articleId}/comments")
    public ResponseEntity<CommentsResponse> findByArticleId(
            @AuthenticationPrinciple AppMember appMember,
            @PathVariable Long articleId
    ) {
        return ResponseEntity.ok(commentService.getAllByArticleId(appMember, articleId));
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Void> update(
            @AuthenticationPrinciple AppMember appMember,
            @PathVariable Long commentId,
            @RequestBody CommentRequest commentRequest) {
        commentService.update(appMember, commentId, commentRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> delete(
            @AuthenticationPrinciple AppMember appMember,
            @PathVariable Long commentId
    ) {
        commentService.delete(appMember, commentId);
        return ResponseEntity.noContent().build();
    }
}
