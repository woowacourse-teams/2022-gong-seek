package com.woowacourse.gongseek.like.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class Likes {

    @OneToMany(mappedBy = "article", orphanRemoval = true)
    private List<Like> values;

    public Likes() {
        this.values = new ArrayList<>();
    }

    public void add(Like like) {
        values.add(like);
    }

    public void remove(Like like) {
        values.remove(like);
    }

    public int getLikeCount() {
        return values.size();
    }
}
