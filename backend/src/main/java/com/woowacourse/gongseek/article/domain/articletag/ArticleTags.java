package com.woowacourse.gongseek.article.domain.articletag;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.tag.domain.Tags;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Embeddable
public class ArticleTags {

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private List<ArticleTag> articleTags;

    public ArticleTags() {
        this(new ArrayList<>());
    }

    public void addTags(Article article, Tags tags) {
        tags.getTags().forEach(tag -> articleTags.add(new ArticleTag(article, tag)));
    }
}
