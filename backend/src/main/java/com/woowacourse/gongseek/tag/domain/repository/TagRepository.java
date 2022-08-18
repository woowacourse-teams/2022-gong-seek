package com.woowacourse.gongseek.tag.domain.repository;

import com.woowacourse.gongseek.tag.domain.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByNameIgnoreCase(String name);

    void deleteByNameIgnoreCaseIn(List<String> names);
}
