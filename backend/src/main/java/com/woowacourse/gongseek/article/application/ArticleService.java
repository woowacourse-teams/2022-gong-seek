package com.woowacourse.gongseek.article.application;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateResponse;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public ArticleIdResponse save(AppMember appMember, ArticleRequest articleRequest) {
        validateGuest(appMember);
        Article article = articleRepository.save(articleRequest.toEntity(getMember(appMember)));

        return new ArticleIdResponse(article);
    }

    @Transactional(readOnly = true)
    public ArticleResponse getOne(AppMember appMember, Long id) {
        Article article = getArticle(id);
        article.addViews();
        if (appMember.isGuest()) {
            return new ArticleResponse(article, false);
        }
        return new ArticleResponse(article, article.isAuthor(getMember(appMember)));
    }

    public ArticleUpdateResponse update(AppMember appMember, ArticleUpdateRequest articleUpdateRequest, Long id) {
        Article article = checkAuthorization(appMember, id);
        article.update(articleUpdateRequest.getTitle(), articleUpdateRequest.getContent());

        return new ArticleUpdateResponse(article);
    }

    public void delete(AppMember appMember, Long id) {
        Article article = checkAuthorization(appMember, id);
        articleRepository.delete(article);
    }

    private void validateGuest(AppMember appMember) {
        if (appMember.isGuest()) {
            throw new IllegalArgumentException("권한이 없는 사용자입니다.");
        }
    }

    private Member getMember(AppMember appMember) {
        return memberRepository.findById(appMember.getPayload())
                .orElseThrow(() -> new IllegalStateException("회원이 존재하지 않습니다."));
    }

    private Article getArticle(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }

    private Article checkAuthorization(AppMember appMember, Long id) {
        validateGuest(appMember);
        Article article = getArticle(id);
        Member member = getMember(appMember);
        validateAuthor(article, member);
        return article;
    }

    private void validateAuthor(Article article, Member member) {
        if (!article.isAuthor(member)) {
            throw new IllegalStateException("작성자만 권한이 있습니다.");
        }
    }
}
