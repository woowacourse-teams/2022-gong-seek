package com.woowacourse.gongseek.vote.domain.repository.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class VoteItemDto {

    private Long id;
    private String content;
    private Long amount;
}
