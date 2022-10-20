package com.woowacourse.gongseek.vote.application;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.exception.ArticleNotFoundException;
import com.woowacourse.gongseek.auth.exception.NotAuthorException;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import com.woowacourse.gongseek.vote.domain.Vote;
import com.woowacourse.gongseek.vote.domain.VoteHistory;
import com.woowacourse.gongseek.vote.domain.VoteItem;
import com.woowacourse.gongseek.vote.domain.VoteItems;
import com.woowacourse.gongseek.vote.domain.repository.VoteHistoryRepository;
import com.woowacourse.gongseek.vote.domain.repository.VoteItemRepository;
import com.woowacourse.gongseek.vote.domain.repository.VoteRepository;
import com.woowacourse.gongseek.vote.exception.UnavailableArticleException;
import com.woowacourse.gongseek.vote.exception.VoteItemNotFoundException;
import com.woowacourse.gongseek.vote.exception.VoteNotFoundException;
import com.woowacourse.gongseek.vote.presentation.dto.SelectVoteItemIdRequest;
import com.woowacourse.gongseek.vote.presentation.dto.VoteCreateRequest;
import com.woowacourse.gongseek.vote.presentation.dto.VoteCreateResponse;
import com.woowacourse.gongseek.vote.presentation.dto.VoteResponse;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final VoteItemRepository voteItemRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final VoteHistoryRepository voteHistoryRepository;

    public VoteCreateResponse create(AppMember appMember, Long articleId, VoteCreateRequest voteCreateRequest) {
        Member member = getMember(appMember);

        Article article = getArticle(articleId);
        validateAuthor(member, article);
        validateCategory(article);
        Vote vote = voteRepository.save(new Vote(article, voteCreateRequest.getExpiryDate()));

        Set<VoteItem> voteItems = VoteItems.of(voteCreateRequest.getItems(), vote).getVoteItems();
        voteItemRepository.saveAll(voteItems);
        return new VoteCreateResponse(vote);
    }

    private Member getMember(AppMember appMember) {
        return memberRepository.findById(appMember.getPayload())
                .orElseThrow(() -> new MemberNotFoundException(appMember.getPayload()));
    }

    private Article getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));
    }

    private void validateAuthor(Member member, Article article) {
        if (!article.isAuthor(member)) {
            throw new NotAuthorException(article.getId(), member.getId());
        }
    }

    private void validateCategory(Article article) {
        if (article.cannotCreateVote()) {
            throw new UnavailableArticleException(article.getId());
        }
    }

    @Transactional(readOnly = true)
    public VoteResponse getOne(Long articleId, AppMember appMember) {
        if (!articleRepository.existsById(articleId)) {
            throw new ArticleNotFoundException(articleId);
        }
        Vote foundVote = getVoteByArticleId(articleId);
        List<VoteItem> voteItems = voteItemRepository.findAllByVoteArticleId(articleId);

        VoteHistory voteHistory = voteHistoryRepository.findByVoteItemsAndMemberId(voteItems, appMember.getPayload())
                .orElse(null);
        return VoteResponse.of(foundVote.getArticle().getId(), voteItems, getVotedItemIdOrNull(voteHistory),
                foundVote.isExpired());
    }

    private Vote getVoteByArticleId(Long articleId) {
        return voteRepository.findByArticleId(articleId)
                .orElseThrow(() -> new VoteNotFoundException(articleId));
    }

    private Long getVotedItemIdOrNull(VoteHistory voteHistory) {
        if (Objects.isNull(voteHistory)) {
            return null;
        }
        return voteHistory.getVoteItem().getId();
    }

    @Retryable(value = ObjectOptimisticLockingFailureException.class, backoff = @Backoff(100))
    public void doVote(Long articleId, AppMember appMember, SelectVoteItemIdRequest selectVoteItemIdRequest) {
        Vote vote = getVoteByArticleId(articleId);
        Member member = getMember(appMember);
        VoteItem voteItem = getVoteItem(selectVoteItemIdRequest.getVoteItemId());

        voteHistoryRepository.findByVoteIdInAndMemberId(vote.getId(), member.getId())
                .ifPresentOrElse(
                        voteHistory -> voteHistory.changeVoteItem(voteItem),
                        () -> saveVoteHistory(member, voteItem)
                );
    }

    private void saveVoteHistory(Member member, VoteItem voteItem) {
        voteItem.increaseAmount();
        voteHistoryRepository.save(new VoteHistory(member, voteItem));
    }

    private VoteItem getVoteItem(Long voteItemId) {
        return voteItemRepository.findById(voteItemId)
                .orElseThrow(() -> new VoteItemNotFoundException(voteItemId));
    }
}
