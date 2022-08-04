package com.woowacourse.gongseek.vote.domain;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.vote.exception.InvalidVoteExpiryAtException;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiryAt;

    public Vote(Article article, LocalDateTime expiryAt) {
        this(null, article, LocalDateTime.now(), expiryAt);
    }

    public Vote(Long id, Article article, LocalDateTime createdAt, LocalDateTime expiryAt) {
        validateNull(expiryAt);
        validateExpiryDate(createdAt, expiryAt);
        this.id = id;
        this.article = article;
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
