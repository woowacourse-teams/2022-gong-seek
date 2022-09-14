package com.woowacourse.gongseek.article.domain;

import com.woowacourse.gongseek.article.presentation.dto.TempArticleRequest;
import com.woowacourse.gongseek.member.domain.Member;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
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

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
public class TempArticle {

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
    private TempTags tempTags;

    @Column(nullable = false)
    private boolean isAnonymous;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public TempArticle(String title, String content, Category category, Member member, List<String> tempTags,
                       boolean isAnonymous) {
        this(
                null,
                new Title(title),
                new Content(content),
                category,
                member,
                new TempTags(tempTags),
                isAnonymous,
                LocalDateTime.now()
        );
    }

    public void update(TempArticleRequest updateRequest) {
        this.title = new Title(updateRequest.getTitle());
        this.content = new Content(updateRequest.getContent());
        this.category = Category.from(updateRequest.getCategory());
        this.tempTags = new TempTags(updateRequest.getTags());
        this.isAnonymous = updateRequest.getIsAnonymous();
        this.createdAt = LocalDateTime.now();
    }

    public List<String> getTempTags() {
        return tempTags.toResponse();
    }
}
