package com.woowacourse.gongseek.vote.presentation;

import com.woowacourse.gongseek.auth.presentation.anntation.AuthenticationPrinciple;
import com.woowacourse.gongseek.auth.presentation.anntation.LoginMember;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import com.woowacourse.gongseek.vote.application.VoteService;
import com.woowacourse.gongseek.vote.presentation.dto.SelectVoteItemIdRequest;
import com.woowacourse.gongseek.vote.presentation.dto.VoteCreateRequest;
import com.woowacourse.gongseek.vote.presentation.dto.VoteCreateResponse;
import com.woowacourse.gongseek.vote.presentation.dto.VoteResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/articles/{articleId}/votes")
@RestController
public class VoteController {

    private final VoteService voteService;

    @LoginMember
    @PostMapping
    public ResponseEntity<VoteCreateResponse> create(
            @AuthenticationPrinciple AppMember appMember,
            @PathVariable Long articleId,
            @Valid @RequestBody VoteCreateRequest voteCreateRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(voteService.create(appMember, articleId, voteCreateRequest));
    }

    @GetMapping
    public ResponseEntity<VoteResponse> getOne(
            @AuthenticationPrinciple AppMember appMember,
            @PathVariable Long articleId) {
        return ResponseEntity.ok(voteService.getOne(articleId, appMember));
    }

    @LoginMember
    @PostMapping("/do")
    public ResponseEntity<Void> doVote(
            @AuthenticationPrinciple AppMember appMember,
            @PathVariable Long articleId,
            @RequestBody SelectVoteItemIdRequest request) {
        voteService.doVote(articleId, appMember, request);
        return ResponseEntity.noContent().build();
    }
}
