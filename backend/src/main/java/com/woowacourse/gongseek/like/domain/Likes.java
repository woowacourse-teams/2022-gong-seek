package com.woowacourse.gongseek.like.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import lombok.Getter;

@Getter
public class Likes {

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Like> values;

    public Likes() {
        values = new ArrayList<>();
    }
}
