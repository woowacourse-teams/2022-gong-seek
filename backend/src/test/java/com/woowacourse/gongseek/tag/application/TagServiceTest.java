package com.woowacourse.gongseek.tag.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepositoryCustom;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.support.DatabaseCleaner;
import com.woowacourse.gongseek.support.IntegrationTest;
import com.woowacourse.gongseek.tag.domain.Tag;
import com.woowacourse.gongseek.tag.domain.Tags;
import com.woowacourse.gongseek.tag.domain.repository.TagRepository;
import com.woowacourse.gongseek.tag.presentation.dto.TagsResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
class TagServiceTest extends IntegrationTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleRepositoryCustom articleRepositoryCustom;

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
        Tags tags = tagService.getOrCreateTags(new Tags(List.of(new Tag("SPRING"))));

        assertAll(
                () -> assertThat(tags.getTags().get(0).getName()).isEqualTo("SPRING"),
                () -> assertThat(tagRepository.findAll()).hasSize(1)
        );
    }

    @Test
    void 태그가_이미_존재하면_태그를_찾아온다() {
        tagRepository.save(new Tag("SPRING"));

        Tags tags = tagService.getOrCreateTags(new Tags(List.of(new Tag("SPRING"))));

        assertAll(
                () -> assertThat(tags.getTags().get(0).getName()).isEqualTo("SPRING"),
                () -> assertThat(tagRepository.findAll()).hasSize(1)
        );
    }

    @Test
    void 태그를_모두_조회한다() {
        tagRepository.save(new Tag("SPRING"));
        tagRepository.save(new Tag("Java"));
        tagRepository.save(new Tag("React"));

        TagsResponse response = tagService.getAll();

        assertAll(
                () -> assertThat(response.getTag()).hasSize(3),
                () -> assertThat(response.getTag()).contains("SPRING", "JAVA", "REACT")
        );
    }

    @Test
    void 태그를_삭제한다() {
        Tag spring = tagRepository.save(new Tag("SPRING"));
        tagRepository.save(new Tag("Java"));
        tagRepository.save(new Tag("React"));

        tagService.deleteAll(List.of(spring.getId()));

        TagsResponse response = tagService.getAll();

        assertAll(
                () -> assertThat(response.getTag()).hasSize(2),
                () -> assertThat(response.getTag().get(0)).isEqualTo("JAVA"),
                () -> assertThat(response.getTag().get(1)).isEqualTo("REACT")
        );
    }

    @Transactional
    @Test
    void 태그를_삭제하면_ArticleTag도_삭제된다() {
        Tag spring = tagRepository.save(new Tag("SPRING"));
        Tag java = tagRepository.save(new Tag("Java"));
        Tag react = tagRepository.save(new Tag("React"));

        Article firstArticle = articleRepository.save(
                new Article("title", "content", Category.QUESTION, member, false));
        firstArticle.addTag(new Tags(List.of(spring)));
        Article secondArticle = articleRepository.save(
                new Article("title", "content", Category.QUESTION, member, false));
        secondArticle.addTag(new Tags(List.of(spring, react)));
        Article thirdArticle = articleRepository.save(
                new Article("title", "content", Category.QUESTION, member, false));
        thirdArticle.addTag(new Tags(List.of(react)));
        Article fourthArticle = articleRepository.save(
                new Article("title", "content", Category.QUESTION, member, false));
        fourthArticle.addTag(new Tags(List.of(spring, java)));

        tagService.deleteAll(List.of(spring.getId(), java.getId()));

        assertThat(articleRepositoryCustom.existsArticleByTagId(spring.getId())).isFalse();
        assertThat(articleRepositoryCustom.existsArticleByTagId(java.getId())).isFalse();
        assertThat(articleRepositoryCustom.existsArticleByTagId(react.getId())).isTrue();
    }
}
