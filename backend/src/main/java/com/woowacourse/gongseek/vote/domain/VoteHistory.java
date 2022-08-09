package com.woowacourse.gongseek.vote.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class VoteHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private Long voteId;

    private Long voteItemId;

    public VoteHistory(Long memberId, Long voteId, Long voteItemId) {
        this(null, memberId, voteId, voteItemId);
    }

    public boolean isSelectSameVoteItem(Long voteItemId) {
        return this.voteItemId.equals(voteItemId);
    }
}
