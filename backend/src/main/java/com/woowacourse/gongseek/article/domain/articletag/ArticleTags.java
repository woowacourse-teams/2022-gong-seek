package com.woowacourse.gongseek.article.domain.articletag;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.tag.domain.Tags;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;

@Getter
@Embeddable
public class ArticleTags {

    @OneToMany(mappedBy = "article", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<ArticleTag> value;

    public ArticleTags() {
        this.value = new ArrayList<>();
    }

    public void add(Article article, Tags tags) {
        tags.getTags()
                .forEach(tag -> value.add(new ArticleTag(article, tag)));
    }

    public void clear() {
        value.clear();
    }

    public List<String> getTagNames() {
        return value.stream()
                .map(articleTag -> articleTag.getTag().getName())
                .collect(Collectors.toList());
    }
}
