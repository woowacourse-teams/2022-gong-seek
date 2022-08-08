package com.woowacourse.gongseek.like.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import lombok.Getter;

@Embeddable
@Getter
public class Likes {

    @OneToMany(mappedBy = "article", orphanRemoval = true)
    private List<Like> values;

    public Likes() {
        this.values = new ArrayList<>();
    }
}
