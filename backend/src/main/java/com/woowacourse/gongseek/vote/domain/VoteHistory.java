package com.woowacourse.gongseek.vote.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class VoteHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    private Long memberId;

    @OnDelete(action = OnDeleteAction.CASCADE)
    private Long voteId;

    @OnDelete(action = OnDeleteAction.CASCADE)
    private Long voteItemId;

    public VoteHistory(Long memberId, Long voteId, Long voteItemId) {
        this(null, memberId, voteId, voteItemId);
    }
}
