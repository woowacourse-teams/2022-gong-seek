package com.woowacourse.gongseek.article.application;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.exception.ArticleNotFoundException;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticlePageResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticlePreviewResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateResponse;
import com.woowacourse.gongseek.auth.exception.NotAuthorException;
import com.woowacourse.gongseek.auth.exception.NotMemberException;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import com.woowacourse.gongseek.comment.domain.repository.CommentRepository;
import com.woowacourse.gongseek.like.domain.repository.LikeRepository;
import com.woowacourse.gongseek.like.presentation.dto.LikeResponse;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import com.woowacourse.gongseek.tag.application.TagService;
import com.woowacourse.gongseek.tag.domain.Tags;
import com.woowacourse.gongseek.vote.domain.repository.VoteRepository;
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
    private final TempArticleService tempArticleService;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;
    private final TagService tagService;
    private final LikeRepository likeRepository;

    public ArticleIdResponse save(AppMember appMember, ArticleRequest articleRequest) {
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
        List<String> tagNames = article.getTagNames();
        article.addViews();
        LikeResponse likeResponse = new LikeResponse(isLike(article, appMember), getLikeCount(article));

        return checkGuest(article, tagNames, appMember, voteRepository.existsByArticleId(article.getId()),
                likeResponse);
    }

    private Article getArticle(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    private Long getLikeCount(Article article) {
        return likeRepository.countByArticleId(article.getId());
    }

    private boolean isLike(Article article, AppMember appMember) {
        return likeRepository.existsByArticleIdAndMemberId(article.getId(), appMember.getPayload());
    }

    private ArticleResponse checkGuest(Article article, List<String> tagNames, AppMember appMember, boolean hasVote,
                                       LikeResponse likeResponse) {
        if (appMember.isGuest()) {
            return ArticleResponse.of(article, tagNames, false, hasVote, likeResponse);
        }
        return ArticleResponse.of(article, tagNames, article.isAuthor(getMember(appMember)), hasVote, likeResponse);
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse getAll(Long cursorId, Integer cursorViews, String category, String sortType,
                                      Pageable pageable, AppMember appMember) {
        Slice<Article> articles = articleRepository.findAllByPage(cursorId, cursorViews, category, sortType, pageable);
        List<ArticlePreviewResponse> responses = createResponse(appMember, articles);

        return new ArticlePageResponse(responses, articles.hasNext());
    }

    private List<ArticlePreviewResponse> createResponse(AppMember appMember, Slice<Article> articles) {
        return articles.getContent().stream()
                .map(it -> getArticlePreviewResponse(it, appMember))
                .collect(Collectors.toList());
    }

    private ArticlePreviewResponse getArticlePreviewResponse(Article article, AppMember appMember) {
        List<String> tagNames = article.getTagNames();
        return ArticlePreviewResponse.of(article, tagNames, getCommentCount(article),
                new LikeResponse(isLike(article, appMember), getLikeCount(article)));
    }

    private int getCommentCount(Article article) {
        return commentRepository.countByArticleId(article.getId());
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse searchByText(Long cursorId, Pageable pageable, String searchText, AppMember appMember) {
        if (searchText.isBlank()) {
            return new ArticlePageResponse(new ArrayList<>(), false);
        }
        Slice<Article> articles = articleRepository.searchByContainingText(cursorId, searchText, pageable);
        List<ArticlePreviewResponse> responses = createResponse(appMember, articles);

        return new ArticlePageResponse(responses, articles.hasNext());
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse searchByAuthor(Long cursorId, Pageable pageable, String authorName,
                                              AppMember appMember) {
        if (authorName.isBlank()) {
            return new ArticlePageResponse(new ArrayList<>(), false);
        }
        Slice<Article> articles = articleRepository.searchByAuthor(cursorId, authorName, pageable);

        List<ArticlePreviewResponse> response = createResponse(appMember, articles);
        return new ArticlePageResponse(response, articles.hasNext());
    }

    public ArticleUpdateResponse update(AppMember appMember, ArticleUpdateRequest articleUpdateRequest, Long id) {
        Article article = checkAuthorization(appMember, id);
        List<String> existingTagNames = article.getTagNames();
        List<String> updatedTagNames = articleUpdateRequest.getTag();
        Tags tags = Tags.from(updatedTagNames);
        Tags foundTags = tagService.getOrCreateTags(tags);
        article.update(articleUpdateRequest.getTitle(), articleUpdateRequest.getContent());
        article.updateTag(foundTags);
        deleteUnusedTags(existingTagNames, updatedTagNames);

        return new ArticleUpdateResponse(article);
    }

    private void deleteUnusedTags(List<String> existingTagNames, List<String> updatedTagNames) {
        updatedTagNames = updatedTagNames.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        existingTagNames.removeAll(updatedTagNames);
        List<String> deletedTagNames = getDeletedTagNames(existingTagNames);
        tagService.delete(deletedTagNames);
    }

    private List<String> getDeletedTagNames(List<String> tagNames) {
        return tagNames.stream()
                .filter(tagName -> !articleRepository.existsArticleByTagName(tagName))
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
        articleRepository.delete(article);

        List<String> deletedTagNames = getDeletedTagNames(article.getTagNames());
        tagService.delete(deletedTagNames);
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse searchByTag(Long cursorId, Pageable pageable, String tagsText, AppMember appMember) {
        Slice<Article> articles = articleRepository.searchByTag(cursorId, extract(tagsText), pageable);
        List<ArticlePreviewResponse> response = createResponse(appMember, articles);
        return new ArticlePageResponse(response, articles.hasNext());
    }

    private List<String> extract(String tagsText) {
        return Arrays.asList(tagsText.split(","));
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse getAllByLikes(Long cursorId, Long likes, String category, Pageable pageable,
                                             AppMember appMember) {
        Slice<Article> articles = articleRepository.findAllByLikes(cursorId, likes, category, pageable);
        List<ArticlePreviewResponse> response = articles.getContent().stream()
                .map(it -> getArticlePreviewResponse(it, appMember))
                .collect(Collectors.toList());

        return new ArticlePageResponse(response, articles.hasNext());
    }
}
