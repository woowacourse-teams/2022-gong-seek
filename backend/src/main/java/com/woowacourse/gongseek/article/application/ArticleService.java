package com.woowacourse.gongseek.article.application;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleResponse;
import com.woowacourse.gongseek.auth.presentation.dto.SearchMember;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.presentation.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public ArticleIdResponse save(SearchMember searchMember, ArticleRequest articleRequest) {
        Member member = memberRepository.findById(searchMember.getPayload())
                .orElseThrow(() -> new IllegalStateException("회원이 존재하지 않습니다."));
        Article article = articleRepository.save(articleRequest.toEntity(member));

        return new ArticleIdResponse(article);
    }

    public ArticleResponse findOne(SearchMember searchMember, Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        if (searchMember.isGuest()) {
            return new ArticleResponse(article, false);
        }
        Member member = memberRepository.findById(searchMember.getPayload())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        return new ArticleResponse(article, article.isAuthor(member));
    }
}
