package com.woowacourse.gongseek.article.application;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.auth.presentation.dto.LoginMember;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public ArticleIdResponse save(LoginMember loginMember, ArticleRequest articleRequest) {
        Member member = memberRepository.findById(loginMember.getPayload())
                .orElseThrow(() -> new IllegalStateException("회원이 존재하지 않습니다."));

        Article article = articleRepository.save(articleRequest.toEntity(member));
        return new ArticleIdResponse(article);
    }

    public Article find(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalStateException("게시글이 존재하지 않습니다."));
    }
}
