package com.woowacourse.gongseek.comment.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.comment.domain.Comment;
import com.woowacourse.gongseek.config.JpaAuditingConfig;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(JpaAuditingConfig.class)
@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void 댓글을_저장한다() {
        Member member = new Member("jurl", "jurlring", "");
        Article article = new Article("title", "content", Category.QUESTION, member);
        Comment comment = new Comment("content", member, article);
        memberRepository.save(member);
        articleRepository.save(article);

        Comment savedComment = commentRepository.save(comment);

        assertThat(savedComment).isSameAs(comment);
    }
}