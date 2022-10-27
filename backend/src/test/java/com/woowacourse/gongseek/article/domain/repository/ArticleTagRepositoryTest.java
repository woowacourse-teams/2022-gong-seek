package com.woowacourse.gongseek.article.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.comment.domain.repository.CommentRepository;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.support.RepositoryTest;
import com.woowacourse.gongseek.tag.domain.Tag;
import com.woowacourse.gongseek.tag.domain.Tags;
import com.woowacourse.gongseek.tag.domain.repository.TagRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("NonAsciiCharacters")
@RepositoryTest
public class ArticleTagRepositoryTest {

    private final Member member = new Member("slo", "hanull", "avatar.com");

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleTagRepository articleTagRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(member);
    }

    @Test
    void 특정_해시태그로_저장되어_있는_게시글이_있는지_확인한다() {
        Article article = articleRepository.save(
                new Article("title1", "content1", Category.QUESTION, member, false));
        List<Tag> tags = List.of(new Tag("spring"), new Tag("java"));
        List<Long> tagIds = tagRepository.saveAll(tags).stream()
                .map(Tag::getId)
                .collect(Collectors.toList());
        article.addTag(new Tags(tags));

        boolean firstResult = articleTagRepository.existsArticleByTagId(tagIds.get(0));
        boolean secondResult = articleTagRepository.existsArticleByTagId(tagIds.get(1));
        boolean thirdResult = articleTagRepository.existsArticleByTagId(999L);

        assertAll(
                () -> assertThat(firstResult).isTrue(),
                () -> assertThat(secondResult).isTrue(),
                () -> assertThat(thirdResult).isFalse()
        );
    }
}
