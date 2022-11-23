package com.woowacourse.gongseek.article.application;

import com.woowacourse.gongseek.article.application.dto.ArticleRequest;
import com.woowacourse.gongseek.article.application.dto.TempArticleDetailResponse;
import com.woowacourse.gongseek.article.application.dto.TempArticleIdResponse;
import com.woowacourse.gongseek.article.application.dto.TempArticleResponse;
import com.woowacourse.gongseek.article.application.dto.TempArticlesResponse;
import com.woowacourse.gongseek.article.domain.TempArticle;
import com.woowacourse.gongseek.article.domain.repository.TempArticleRepository;
import com.woowacourse.gongseek.article.exception.TempArticleNotFoundException;
import com.woowacourse.gongseek.auth.application.dto.AppMember;
import com.woowacourse.gongseek.auth.exception.NotAuthorException;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TempArticleService {

    private final MemberRepository memberRepository;
    private final TempArticleRepository tempArticleRepository;

    @Transactional
    public TempArticleIdResponse createOrUpdate(AppMember appMember, ArticleRequest tempArticleRequest) {
        Member member = getMember(appMember.getPayload());

        if (isExistTempArticle(tempArticleRequest.getTempArticleId())) {
            return update(tempArticleRequest);
        }
        return create(tempArticleRequest, member);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    private boolean isExistTempArticle(Long articleTempId) {
        return articleTempId != null && tempArticleRepository.existsById(articleTempId);
    }

    private TempArticleIdResponse update(ArticleRequest request) {
        TempArticle tempArticle = getTempArticle(request.getTempArticleId());
        tempArticle.update(request.toTempArticle(tempArticle.getMember()));
        return new TempArticleIdResponse(tempArticle.getId());
    }

    private TempArticle getTempArticle(Long tempArticleId) {
        return tempArticleRepository.findByIdWithMember(tempArticleId)
                .orElseThrow(() -> new TempArticleNotFoundException(tempArticleId));
    }

    private TempArticleIdResponse create(ArticleRequest request, Member member) {
        TempArticle tempArticle = tempArticleRepository.save(request.toTempArticle(member));
        return new TempArticleIdResponse(tempArticle.getId());
    }

    public TempArticleDetailResponse getOne(AppMember appMember, Long tempArticleId) {
        if (!isExistTempArticle(tempArticleId)) {
            throw new TempArticleNotFoundException(tempArticleId);
        }
        Member member = getMember(appMember.getPayload());
        TempArticle tempArticle = getTempArticle(tempArticleId, member.getId());

        return TempArticleDetailResponse.from(tempArticle);
    }

    private TempArticle getTempArticle(Long tempArticleId, Long memberId) {
        return tempArticleRepository.findByIdAndMemberId(tempArticleId, memberId)
                .orElseThrow(() -> new NotAuthorException(tempArticleId, memberId));
    }

    @Transactional
    public void delete(Long tempArticleId, AppMember appMember) {
        if (!isExistTempArticle(tempArticleId)) {
            return;
        }
        Member member = getMember(appMember.getPayload());
        TempArticle articleTemp = getTempArticle(tempArticleId, member.getId());

        tempArticleRepository.delete(articleTemp);
    }

    public TempArticlesResponse getAll(AppMember appMember) {
        Member member = getMember(appMember.getPayload());
        List<TempArticle> tempArticles = tempArticleRepository.findAllByMemberId(member.getId());
        return new TempArticlesResponse(tempArticles.stream()
                .map(TempArticleResponse::from)
                .collect(Collectors.toList()));
    }
}
