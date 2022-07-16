package com.woowacourse.gongseek.article.application;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleResponse;
import com.woowacourse.gongseek.auth.presentation.dto.User;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public ArticleIdResponse save(User user, ArticleRequest articleRequest) {
        validateGuest(user);
        Member member = memberRepository.findById(user.getPayload())
                .orElseThrow(() -> new IllegalStateException("회원이 존재하지 않습니다."));
        Article article = articleRepository.save(articleRequest.toEntity(member));

        return new ArticleIdResponse(article);
    }

    private void validateGuest(User user) {
        if (user.isGuest()) {
            throw new IllegalArgumentException("권한이 없는 사용자입니다.");
        }
    }

    public ArticleResponse findOne(User user, Long id, String category) {
        Category.from(category);
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        article.addViews();
        if (user.isGuest()) {
            return new ArticleResponse(article, false);
        }
        Member member = memberRepository.findById(user.getPayload())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        return new ArticleResponse(article, article.isAuthor(member));
    }
}
