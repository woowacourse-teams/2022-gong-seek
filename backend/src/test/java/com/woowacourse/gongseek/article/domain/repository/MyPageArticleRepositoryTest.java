package com.woowacourse.gongseek.article.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.dto.MyPageArticleDto;
import com.woowacourse.gongseek.comment.domain.Comment;
import com.woowacourse.gongseek.comment.domain.repository.CommentRepository;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.support.RepositoryTest;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

@SuppressWarnings("NonAsciiCharacters")
@TestConstructor(autowireMode = AutowireMode.ALL)
@AllArgsConstructor
@RepositoryTest
public class MyPageArticleRepositoryTest {

    private final Member member = new Member("slo", "hanull", "avatar.com");

    private final ArticleRepository articleRepository;
    private final MyPageArticleRepository myPageArticleRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(member);
    }

    @Test
    void 마이페이지에서_내가_작성한_게시글을_조회할_때_댓글_갯수도_함께_조회한다() {
        Article firstArticle = articleRepository.save(
                new Article("title1", "content1", Category.DISCUSSION, member, false));
        Article secondArticle = articleRepository.save(
                new Article("title2", "content2", Category.DISCUSSION, member, false));

        commentRepository.save(new Comment("content1", member, firstArticle, false));
        commentRepository.save(new Comment("content2", member, firstArticle, false));
        commentRepository.save(new Comment("content3", member, firstArticle, false));

        commentRepository.save(new Comment("content1", member, secondArticle, false));
        commentRepository.save(new Comment("content2", member, secondArticle, false));

        List<MyPageArticleDto> myPageArticles = myPageArticleRepository.findAllByMemberIdWithCommentCount(
                member.getId());

        assertAll(
                () -> assertThat(myPageArticles).hasSize(2),
                () -> assertThat(myPageArticles.get(0))
                        .usingRecursiveComparison()
                        .ignoringFields("createdAt", "updatedAt")
                        .isEqualTo(
                                new MyPageArticleDto(firstArticle.getId(), firstArticle.getTitle(),
                                        firstArticle.getCategory(),
                                        3L, 0, LocalDateTime.now(), LocalDateTime.now()))
        );
    }
}
