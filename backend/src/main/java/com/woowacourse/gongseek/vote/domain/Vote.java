package com.woowacourse.gongseek.vote.domain;

import com.woowacourse.gongseek.vote.exception.InvalidVoteExpiryAtException;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Vote {

    private static final int MAX_VOTE_PERIOD = 7;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime expiryAt;

    public Vote(LocalDateTime expiryAt) {
        this(null, LocalDateTime.now(), expiryAt);
    }

    public Vote(Long id, LocalDateTime createdAt, LocalDateTime expiryAt) {
        validateNull(expiryAt);
        validateExpiryDate(createdAt, expiryAt);
        this.id = id;
        this.createdAt = createdAt;
        this.expiryAt = expiryAt;
    }

    private void validateNull(LocalDateTime expiryAt) {
        if (Objects.isNull(expiryAt)) {
            throw new InvalidVoteExpiryAtException();
        }
    }

    private void validateExpiryDate(LocalDateTime createdAt, LocalDateTime expiryAt) {
        if (createdAt.isAfter(expiryAt) || expiryAt.isAfter(createdAt.plusDays(MAX_VOTE_PERIOD))) {
            throw new InvalidVoteExpiryAtException();
        }
    }
}
