package com.woowacourse.gongseek.tag.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongseek.support.RepositoryTest;
import com.woowacourse.gongseek.tag.domain.Tag;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
@RepositoryTest
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    void 태그를_저장한다() {
        Tag tag = new Tag("Spring");

        Tag savedTag = tagRepository.save(tag);
        assertThat(savedTag).isSameAs(tag);
    }

    @Test
    void 태그를_조회한다() {
        Tag tag = new Tag("Spring");
        Tag savedTag = tagRepository.save(tag);

        Tag foundTag = tagRepository.findByNameIgnoreCase("Spring")
                .orElse(null);

        assertThat(savedTag).isSameAs(foundTag);
    }

    @Test
    void 태그를_모두_조회한다() {
        tagRepository.save(new Tag("Spring"));
        tagRepository.save(new Tag("Java"));
        tagRepository.save(new Tag("React"));

        List<Tag> tags = tagRepository.findAll();

        assertThat(tags).hasSize(3);
    }
}
