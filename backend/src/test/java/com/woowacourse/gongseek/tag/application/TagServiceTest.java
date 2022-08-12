package com.woowacourse.gongseek.tag.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.common.DatabaseCleaner;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.tag.domain.Tag;
import com.woowacourse.gongseek.tag.domain.Tags;
import com.woowacourse.gongseek.tag.domain.repository.TagRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
class TagServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private Member member;

    @BeforeEach
    void setUp() {
        member = memberRepository.save(new Member("slo", "hanull", "avatar.com"));
    }

    @AfterEach
    void tearDown() {
        databaseCleaner.tableClear();
    }

    @Test
    void 태그가_존재하지_않으면_태그를_생성한다() {
        List<Tag> tags = tagService.getOrCreateTags(new Tags(List.of(new Tag("SPRING"))));

        assertAll(
                () -> assertThat(tags.get(0).getName().getValue()).isEqualTo("SPRING"),
                () -> assertThat(tagRepository.findAll()).hasSize(1)
        );
    }

    @Test
    void 태그가_이미_존재하면_태그를_찾아온다() {
        tagRepository.save(new Tag("SPRING"));

        List<Tag> tags = tagService.getOrCreateTags(new Tags(List.of(new Tag("SPRING"))));

        assertAll(
                () -> assertThat(tags.get(0).getName().getValue()).isEqualTo("SPRING"),
                () -> assertThat(tagRepository.findAll()).hasSize(1)
        );
    }
}
