package com.woowacourse.gongseek.tag.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.support.DatabaseCleaner;
import com.woowacourse.gongseek.tag.domain.Tag;
import com.woowacourse.gongseek.tag.domain.Tags;
import com.woowacourse.gongseek.tag.domain.repository.TagRepository;
import com.woowacourse.gongseek.tag.presentation.dto.TagsResponse;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
class TagServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleRepository articleRepository;

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
                () -> assertThat(response.getTag().get(0)).isEqualTo("SPRING"),
                () -> assertThat(response.getTag().get(1)).isEqualTo("JAVA"),
                () -> assertThat(response.getTag().get(2)).isEqualTo("REACT")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"SPRING", "spring", "Spring"})
    void 대소문자_상관없이_태그를_삭제한다(String name) {
        tagRepository.save(new Tag("SPRING"));
        tagRepository.save(new Tag("Java"));
        tagRepository.save(new Tag("React"));

        tagService.delete(List.of(name));

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

        tagService.delete(List.of("spring", "java"));

        assertAll(
                () -> assertThat(articleRepository.existsArticleByTagName("SPRING")).isFalse(),
                () -> assertThat(articleRepository.existsArticleByTagName("java")).isFalse(),
                () -> assertThat(articleRepository.existsArticleByTagName("REACT")).isTrue()
        );
    }
}
