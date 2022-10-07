package com.woowacourse.gongseek.auth.domain;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(generator = "uuid4")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

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
