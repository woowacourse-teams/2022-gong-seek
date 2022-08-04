package com.woowacourse.gongseek.vote.application;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.exception.ArticleNotFoundException;
import com.woowacourse.gongseek.auth.exception.NoAuthorizationException;
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
import com.woowacourse.gongseek.vote.exception.AlreadyVoteSameItemException;
import com.woowacourse.gongseek.vote.exception.CannotCreateVoteException;
import com.woowacourse.gongseek.vote.exception.VoteItemNotFoundException;
import com.woowacourse.gongseek.vote.exception.VoteNotFoundException;
import com.woowacourse.gongseek.vote.presentation.dto.SelectVoteItemIdRequest;
import com.woowacourse.gongseek.vote.presentation.dto.VoteCreateRequest;
import com.woowacourse.gongseek.vote.presentation.dto.VoteCreateResponse;
import com.woowacourse.gongseek.vote.presentation.dto.VoteItemResponse;
import com.woowacourse.gongseek.vote.presentation.dto.VoteResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
        validateGuest(appMember);
        Member member = getMember(appMember);
        Article article = getArticle(articleId);
        validateAuthor(member, article);
        validateCategory(article);
        Vote vote = voteRepository.save(new Vote(article, voteCreateRequest.getExpiryDate()));

        Set<VoteItem> voteItems = VoteItems.of(voteCreateRequest.getItems(), vote).getVoteItems();
        voteItemRepository.saveAll(voteItems);
        return new VoteCreateResponse(vote);
    }

    private void validateGuest(AppMember appMember) {
        if (appMember.isGuest()) {
            throw new NoAuthorizationException();
        }
    }

    private Member getMember(AppMember appMember) {
        return memberRepository.findById(appMember.getPayload())
                .orElseThrow(MemberNotFoundException::new);
    }

    private Article getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(ArticleNotFoundException::new);
    }

    private void validateAuthor(Member member, Article article) {
        if (!article.isAuthor(member)) {
            throw new NoAuthorizationException();
        }
    }

    private void validateCategory(Article article) {
        if (article.cannotCreateVote()) {
            throw new CannotCreateVoteException();
        }
    }

    public VoteResponse getOne(Long articleId, AppMember appMember) {
        if (!articleRepository.existsById(articleId)) {
            throw new ArticleNotFoundException();
        }
        Vote foundVote = getVote(articleId);
        List<VoteItem> voteItems = voteItemRepository.findAllByVoteArticleId(articleId);

        VoteHistory voteHistory = voteHistoryRepository.findByVoteIdAndMemberId(foundVote.getId(),
                appMember.getPayload()).orElse(null);

        return new VoteResponse(foundVote, convertVoteItemResponse(voteItems), voteHistory,
                isExpired(foundVote.getExpiryAt()));
    }

    private List<VoteItemResponse> convertVoteItemResponse(List<VoteItem> voteItems) {
        return voteItems.stream()
                .map(VoteItemResponse::new)
                .collect(Collectors.toList());
    }

    private boolean isExpired(LocalDateTime expiryAt) {
        return expiryAt.isBefore(LocalDateTime.now());
    }

    public void doVote(Long articleId, AppMember appMember, SelectVoteItemIdRequest selectVoteItemIdRequest) {
        validateGuest(appMember);
        Vote vote = voteRepository.findByArticleId(articleId)
                .orElseThrow(ArticleNotFoundException::new);
        Member member = getMember(appMember);
        VoteItem selectedVoteItem = getVoteItem(selectVoteItemIdRequest.getVoteItemId());

        voteHistoryRepository.findByVoteIdAndMemberId(vote.getId(), member.getId())
                .ifPresentOrElse(
                        voteHistory -> updateVoteHistory(vote.getId(), member.getId(), selectedVoteItem, voteHistory),
                        () -> saveVoteHistory(vote, member, selectedVoteItem)
                );
    }

    private Vote getVote(Long voteId) {
        return voteRepository.findById(voteId)
                .orElseThrow(VoteNotFoundException::new);
    }

    private VoteItem getVoteItem(Long voteItemId) {
        return voteItemRepository.findById(voteItemId)
                .orElseThrow(VoteItemNotFoundException::new);
    }

    private void updateVoteHistory(Long voteId, Long memberId, VoteItem selectedVoteItem, VoteHistory voteHistory) {
        if (voteHistory.isSelectSameVoteItem(selectedVoteItem.getId())) {
            throw new AlreadyVoteSameItemException(selectedVoteItem.getId());
        }
        VoteItem originVoteItem = getVoteItem(voteHistory.getVoteItemId());
        originVoteItem.decreaseAmount();
        voteHistoryRepository.updateHistory(selectedVoteItem.getId(), memberId, voteId);
        selectedVoteItem.increaseAmount();
    }

    private void saveVoteHistory(Vote vote, Member member, VoteItem selectedVoteItem) {
        selectedVoteItem.increaseAmount();
        voteHistoryRepository.save(new VoteHistory(member.getId(), vote.getId(), selectedVoteItem.getId()));
    }
}
