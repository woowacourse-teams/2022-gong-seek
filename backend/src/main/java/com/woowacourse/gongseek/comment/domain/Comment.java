package com.woowacourse.gongseek.comment.domain;


import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.member.domain.Member;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Comment {

    private static final int MAX_CONTENT_LENGTH = 10000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @CreatedDate
    private LocalDateTime createdAt;

    public Comment(String content, Member member, Article article) {
        validateContentLength(content);
        this.content = content;
        this.member = member;
        this.article = article;
    }

    private void validateContentLength(String content) {
        if (Objects.isNull(content) || content.isBlank() || content.length() > MAX_CONTENT_LENGTH) {
            throw new IllegalArgumentException("댓글의 길이는 1~10000이여야 합니다.");
        }
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public boolean isMember(Member member) {
        return this.getMember().equals(member);
    }
}
