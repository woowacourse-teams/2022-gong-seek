package com.woowacourse.gongseek.article.application;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.domain.repository.ArticleTagRepository;
import com.woowacourse.gongseek.article.domain.repository.PagingArticleRepository;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticlePreviewDto;
import com.woowacourse.gongseek.article.exception.ArticleNotFoundException;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticlePageResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateResponse;
import com.woowacourse.gongseek.auth.exception.NotAuthorException;
import com.woowacourse.gongseek.auth.exception.NotMemberException;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import com.woowacourse.gongseek.comment.domain.repository.CommentRepository;
import com.woowacourse.gongseek.like.domain.repository.LikeRepository;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import com.woowacourse.gongseek.tag.application.TagService;
import com.woowacourse.gongseek.tag.domain.Tags;
import com.woowacourse.gongseek.vote.domain.Vote;
import com.woowacourse.gongseek.vote.domain.repository.VoteHistoryRepository;
import com.woowacourse.gongseek.vote.domain.repository.VoteItemRepository;
import com.woowacourse.gongseek.vote.domain.repository.VoteRepository;
import com.woowacourse.gongseek.vote.exception.VoteNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleTagRepository articleTagRepository;
    private final PagingArticleRepository pagingArticleRepository;
    private final TempArticleService tempArticleService;
    private final MemberRepository memberRepository;
    private final VoteRepository voteRepository;
    private final TagService tagService;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final VoteHistoryRepository voteHistoryRepository;
    private final VoteItemRepository voteItemRepository;

    public ArticleIdResponse create(AppMember appMember, ArticleRequest articleRequest) {
        validateGuest(appMember);
        Member member = getMember(appMember);

        Tags foundTags = tagService.getOrCreateTags(Tags.from(articleRequest.getTag()));
        Article article = articleRepository.save(articleRequest.toArticle(member));
        article.addTag(foundTags);

        tempArticleService.delete(articleRequest.getTempArticleId(), appMember);
        return new ArticleIdResponse(article);
    }

    private void validateGuest(AppMember appMember) {
        if (appMember.isGuest()) {
            throw new NotMemberException();
        }
    }

    private Member getMember(AppMember appMember) {
        return memberRepository.findById(appMember.getPayload())
                .orElseThrow(() -> new MemberNotFoundException(appMember.getPayload()));
    }

    public ArticleResponse getOne(AppMember appMember, Long id) {
        Article article = getArticle(id);
        article.addViews();

        return checkGuest(article, appMember, voteRepository.existsByArticleId(article.getId()),
                isLike(article, appMember));
    }

    private Article getArticle(Long id) {
        return articleRepository.findByIdWithAll(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    private boolean isLike(Article article, AppMember appMember) {
        return likeRepository.existsByArticleIdAndMemberId(article.getId(), appMember.getPayload());
    }

    private ArticleResponse checkGuest(Article article, AppMember appMember, boolean hasVote, boolean isLike) {
        if (appMember.isGuest()) {
            return ArticleResponse.of(article, false, hasVote, isLike);
        }
        return ArticleResponse.of(article, article.isAuthor(getMember(appMember)), hasVote, isLike);
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse getAll(Long cursorId, Long cursorViews, String category, String sortType,
                                      Pageable pageable, AppMember appMember) {
        Slice<ArticlePreviewDto> articles = pagingArticleRepository.findAllByPage(cursorId, cursorViews, category,
                sortType, appMember.getPayload(), pageable);
        return ArticlePageResponse.of(articles);
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse searchByText(Long cursorId, Pageable pageable, String searchText, AppMember appMember) {
        if (searchText.isBlank()) {
            return new ArticlePageResponse(new ArrayList<>(), false);
        }
        Slice<ArticlePreviewDto> articles = pagingArticleRepository.searchByContainingText(cursorId, searchText,
                appMember.getPayload(), pageable);
        return ArticlePageResponse.of(articles);
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse searchByAuthor(Long cursorId, Pageable pageable, String authorName,
                                              AppMember appMember) {
        if (authorName.isBlank()) {
            return new ArticlePageResponse(new ArrayList<>(), false);
        }
        Slice<ArticlePreviewDto> articles = pagingArticleRepository.searchByAuthor(cursorId, authorName,
                appMember.getPayload(), pageable);
        return ArticlePageResponse.of(articles);
    }

    public ArticleUpdateResponse update(AppMember appMember, ArticleUpdateRequest articleUpdateRequest, Long id) {
        Article article = checkAuthorization(appMember, id);
        List<Long> existingTagIds = article.getTagIds();
        List<String> updatedTagNames = articleUpdateRequest.getTag();
        Tags updatedTags = tagService.getOrCreateTags(Tags.from(updatedTagNames));
        article.update(articleUpdateRequest.getTitle(), articleUpdateRequest.getContent(), updatedTags);
        deleteUnusedTags(existingTagIds, updatedTags.getTagIds());

        return new ArticleUpdateResponse(article);
    }

    private void deleteUnusedTags(List<Long> existingTagIds, List<Long> updatedTagIds) {
        existingTagIds.removeAll(updatedTagIds);
        List<Long> deletedTagIds = getDeletedTagIds(existingTagIds);
        tagService.deleteAll(deletedTagIds);
    }

    private List<Long> getDeletedTagIds(List<Long> tagIds) {
        return tagIds.stream()
                .filter(tagId -> !articleTagRepository.existsArticleByTagId(tagId))
                .collect(Collectors.toList());
    }

    private Article checkAuthorization(AppMember appMember, Long id) {
        validateGuest(appMember);
        Member member = getMember(appMember);
        Article article = getArticle(id);
        validateAuthor(article, member);
        return article;
    }

    private void validateAuthor(Article article, Member member) {
        if (!article.isAuthor(member)) {
            throw new NotAuthorException(article.getId(), member.getId());
        }
    }

    public void delete(AppMember appMember, Long id) {
        Article article = checkAuthorization(appMember, id);
        deleteVoteHistory(article);
        deleteComment(article);
        deleteLikes(article);
        articleRepository.delete(article);
        List<Long> deletedTagIds = getDeletedTagIds(article.getTagIds());
        tagService.deleteAll(deletedTagIds);
    }

    private void deleteLikes(Article article) {
        likeRepository.deleteAllByArticleId(article.getId());
    }

    private void deleteComment(Article article) {
        commentRepository.deleteAllByArticleId(article.getId());
    }

    private void deleteVoteHistory(Article article) {
        if (article.getCategory().equals(Category.QUESTION)) {
            return;
        }
        Vote vote = voteRepository.findByArticleId(article.getId())
                .orElseThrow(() -> new VoteNotFoundException(article.getId()));
        voteHistoryRepository.deleteAllByVoteItemIn(voteItemRepository.findAllByVoteId(vote.getId()));
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse searchByTag(Long cursorId, Pageable pageable, String tagsText, AppMember appMember) {
        Slice<ArticlePreviewDto> articles = pagingArticleRepository.searchByTag(cursorId, appMember.getPayload(),
                extractTagName(tagsText), pageable);
        return ArticlePageResponse.of(articles);
    }

    private List<String> extractTagName(String tagsText) {
        return Arrays.asList(tagsText.split(","));
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse getAllByLikes(Long cursorId, Long cursorLikes, String category, Pageable pageable,
                                             AppMember appMember) {
        Slice<ArticlePreviewDto> articles = pagingArticleRepository.findAllByLikes(cursorId, cursorLikes, category,
                appMember.getPayload(), pageable);
        return ArticlePageResponse.of(articles);
    }

    public void synchronizeLikeCountAndCommentCount() {
        articleRepository.findAll()
                .forEach(article -> article.updateLikeCountBatch(likeRepository.countByArticleId(article.getId())));
    }
}
