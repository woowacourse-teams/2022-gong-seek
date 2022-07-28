package com.woowacourse.gongseek.article.domain;

import com.woowacourse.gongseek.article.exception.ArticleContentLengthException;
import com.woowacourse.gongseek.article.exception.ArticleTitleEmptyException;
import com.woowacourse.gongseek.article.exception.ArticleTitleTooLongException;
import com.woowacourse.gongseek.member.domain.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Article {

    private static final int INITIAL_VIEWS = 0;
    private static final int MIN_TITLE_LENGTH = 1;
    private static final int MAX_TITLE_LENGTH = 500;
    private static final int MAX_CONTENT_LENGTH = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1_000)
    private String content;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private int views;

    public Article(String title, String content, Category category, Member member) {
        validateTitleLength(title.trim());
        validateContentLength(content);
        this.title = title;
        this.content = content;
        this.category = category;
        this.member = member;
        this.views = INITIAL_VIEWS;
    }

    public boolean isAuthor(Member member) {
        return member.equals(this.getMember());
    }

    public void addViews() {
        this.views++;
    }

    private void validateTitleLength(String title) {
        if (title.length() < MIN_TITLE_LENGTH) {
            throw new ArticleTitleEmptyException();
        }
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new ArticleTitleTooLongException();
        }
    }

    private void validateContentLength(String content) {
        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new ArticleContentLengthException();
        }
    }

    public void update(String title, String content) {
        validateTitleLength(title);
        validateContentLength(content);
        this.title = title;
        this.content = content;
    }
}
