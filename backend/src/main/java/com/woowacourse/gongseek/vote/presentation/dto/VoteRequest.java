package com.woowacourse.gongseek.vote.presentation.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoteRequest {

    private VoteItemRequest voteItemRequest;
    private LocalDateTime expiryDate;
}
