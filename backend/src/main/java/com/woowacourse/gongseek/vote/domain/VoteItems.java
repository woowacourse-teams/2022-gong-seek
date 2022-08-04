package com.woowacourse.gongseek.vote.domain;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class VoteItems {

    private final Set<VoteItem> voteItems;

    public static VoteItems of(Set<String> voteItems, Vote vote) {
        Set<VoteItem> collect = voteItems.stream()
                .map(voteItem -> new VoteItem(voteItem, vote))
                .collect(Collectors.toSet());

        return new VoteItems(collect);
    }

}
