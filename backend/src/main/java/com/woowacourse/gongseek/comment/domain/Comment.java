package com.woowacourse.gongseek.comment.domain;


import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.common.domain.BaseTimeEntity;
import com.woowacourse.gongseek.member.domain.Member;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @Column(nullable = false)
    private boolean isAnonymous;

    public Comment(String content, Member member, Article article, boolean isAnonymous) {
        this.content = new Content(content);
        this.member = member;
        this.article = article;
        this.isAnonymous = isAnonymous;
    }

    public void updateContent(String content) {
        this.content = new Content(content);
    }

    public boolean isAuthor(Member member) {
        return this.member.equals(member);
    }

    public boolean isAnonymousAuthor(String cipherId) {
        return member.isAnonymous(cipherId);
    }

    public String getContent() {
        return content.getValue();
    }
}
