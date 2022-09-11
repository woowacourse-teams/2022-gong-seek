package com.woowacourse.gongseek.auth.domain;

import com.woowacourse.gongseek.member.domain.Member;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    private String value;

    private LocalDateTime expiryDate;

    private RefreshToken(Member member, String value, LocalDateTime expiryDate) {
        this(null, member, value, expiryDate);
    }

    public static RefreshToken create(Member member){
        return new RefreshToken(member, UUID.randomUUID().toString(), LocalDateTime.now().plusDays(7));
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}
