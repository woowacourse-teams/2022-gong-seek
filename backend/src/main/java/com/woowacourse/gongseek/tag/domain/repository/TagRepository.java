package com.woowacourse.gongseek.tag.domain.repository;

import com.woowacourse.gongseek.tag.domain.Name;
import com.woowacourse.gongseek.tag.domain.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(Name name);
}
