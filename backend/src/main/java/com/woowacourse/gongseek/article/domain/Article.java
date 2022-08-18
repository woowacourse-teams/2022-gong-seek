package com.woowacourse.gongseek.article.domain;

import com.woowacourse.gongseek.article.domain.articletag.ArticleTags;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.tag.domain.Tags;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Embedded
    private Views views;

    @Embedded
    private ArticleTags articleTags;

    @Column(nullable = false)
    private boolean isAnonymous;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Article(String title, String content, Category category, Member member, boolean isAnonymous) {
        this(
                null,
                new Title(title),
                new Content(content),
                category, member,
                new Views(),
                new ArticleTags(),
                isAnonymous,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public boolean isAuthor(Member member) {
        return member.equals(this.getMember());
    }

    public boolean isAnonymousAuthor(String cipherId) {
        return member.isAnonymous(cipherId);
    }

    public void addViews() {
        views.addValue();
    }

    public void update(String title, String content) {
        this.title = new Title(title);
        this.content = new Content(content);
    }

    public void addTag(Tags tags) {
        articleTags.add(this, tags);
    }

    public void updateTag(Tags tags) {
        articleTags.clear();
        addTag(tags);
    }

    public boolean cannotCreateVote() {
        return !Category.DISCUSSION.equals(this.category);
    }

    public String getTitle() {
        return title.getValue();
    }

    public String getContent() {
        return content.getValue();
    }

    public int getViews() {
        return views.getValue();
    }

    public List<String> getTagNames() {
        return this.articleTags.getTagNames();
    }
}
