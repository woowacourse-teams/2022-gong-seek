package com.woowacourse.gongseek.article.application;

import com.woowacourse.gongseek.article.domain.ArticleTemp;
import com.woowacourse.gongseek.article.domain.repository.ArticleTempRepository;
import com.woowacourse.gongseek.article.presentation.dto.ArticleTempIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleTempRequest;
import com.woowacourse.gongseek.auth.exception.NotMemberException;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ArticleTempService {

    private final MemberRepository memberRepository;
    private final ArticleTempRepository articleTempRepository;

    @Transactional
    public ArticleTempIdResponse createOrUpdate(AppMember appMember, ArticleTempRequest articleTempRequest) {
        validateGuest(appMember);
        final Member member = getMember(appMember.getPayload());

        if (isExistArticleTemp(articleTempRequest.getId())) {
            return update(articleTempRequest);
        }
        return create(articleTempRequest, member);
    }

    private void validateGuest(AppMember appMember) {
        if (appMember.isGuest()) {
            throw new NotMemberException();
        }
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    private boolean isExistArticleTemp(Long articleTempId) {
        return articleTempId != null && articleTempRepository.existsById(articleTempId);
    }

    private ArticleTempIdResponse update(ArticleTempRequest articleTempRequest) {
        final ArticleTemp articleTemp = getArticleTemp(articleTempRequest.getId());
        articleTemp.update(articleTempRequest);
        return new ArticleTempIdResponse(articleTemp.getId());
    }

    private ArticleTemp getArticleTemp(Long articleTempId) {
        return articleTempRepository.findById(articleTempId)
                .get();
    }

    private ArticleTempIdResponse create(ArticleTempRequest articleTempRequest, Member member) {
        final ArticleTemp articleTemp = articleTempRepository.save(articleTempRequest.toEntity(member));
        return new ArticleTempIdResponse(articleTemp.getId());
    }

    public void delete(Long articleTempId) {
        if (isExistArticleTemp(articleTempId)) {
            articleTempRepository.delete(getArticleTemp(articleTempId));
        }
    }
}
