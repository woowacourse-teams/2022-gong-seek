package com.woowacourse.gongseek.vote.application;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.exception.ArticleNotFoundException;
import com.woowacourse.gongseek.auth.exception.NotAuthorException;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import com.woowacourse.gongseek.member.application.Encryptor;
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
    private final Encryptor encryptor;

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
        if (!isAuthor(article, member)) {
            throw new NotAuthorException(article.getId(), member.getId());
        }
    }

    private boolean isAuthor(Article article, Member member) {
        if (article.isAnonymous()) {
            String cipherId = encryptor.encrypt(String.valueOf(member.getId()));
            return article.isAnonymousAuthor(cipherId);
        }
        return article.isAuthor(member);
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

        VoteHistory voteHistory = voteHistoryRepository.findByVoteIdAndMemberId(foundVote.getId(),
                appMember.getPayload()).orElse(null);
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

    public void doVote(Long articleId, AppMember appMember, SelectVoteItemIdRequest selectVoteItemIdRequest) {
        Vote vote = getVoteByArticleId(articleId);
        Member member = getMember(appMember);
        VoteItem selectedVoteItem = getVoteItem(selectVoteItemIdRequest.getVoteItemId());

        deleteOriginVoteIfExist(vote, member);
        saveVoteHistory(vote, member, selectedVoteItem);
    }

    private void deleteOriginVoteIfExist(Vote vote, Member member) {
        voteHistoryRepository.findByVoteIdAndMemberId(vote.getId(), member.getId()).ifPresent(
                voteHistory-> {
                    voteHistory.getVoteItem().decreaseAmount();
                    voteHistoryRepository.deleteByVoteIdAndMemberId(vote.getId(), member.getId());
                }
        );
    }

    private VoteItem getVoteItem(Long voteItemId) {
        return voteItemRepository.findById(voteItemId)
                .orElseThrow(() -> new VoteItemNotFoundException(voteItemId));
    }

    private void saveVoteHistory(Vote vote, Member member, VoteItem selectedVoteItem) {
        selectedVoteItem.increaseAmount();
        voteHistoryRepository.save(new VoteHistory(member, vote, selectedVoteItem));
    }
}
