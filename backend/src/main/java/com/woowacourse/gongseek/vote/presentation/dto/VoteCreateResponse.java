package com.woowacourse.gongseek.vote.presentation.dto;

import com.woowacourse.gongseek.vote.domain.Vote;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class VoteCreateResponse {

    private Long articleId;

    public VoteCreateResponse(Vote vote) {
        this(vote.getArticle().getId());
    }
}
