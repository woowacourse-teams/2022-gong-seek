package com.woowacourse.gongseek.auth.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "RefreshToken", timeToLive = 1_209_600L)
public class RefreshToken {

    @Id
    @GeneratedValue(generator = "uuid4")
    @GenericGenerator(name = "uuid4", strategy = "uuid4")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Indexed
    private Long memberId;

    private LocalDateTime expiryDate;

    private boolean issue;

    private RefreshToken(Long memberId, LocalDateTime expiryDate, boolean issue) {
        this.memberId = memberId;
        this.expiryDate = expiryDate;
        this.issue = issue;
    }

    public static RefreshToken create(Long memberId) {
        return new RefreshToken(memberId, LocalDateTime.now().plusDays(7), false);
    }

    public void used() {
        this.issue = true;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}
