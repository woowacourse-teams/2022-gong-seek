package com.woowacourse.gongseek.article.application;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepositoryCustom;
import com.woowacourse.gongseek.article.domain.repository.PagingArticleRepository;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticlePreviewDto;
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
import java.util.Map;
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
    private final ArticleRepositoryCustom articleRepositoryCustom;
    private final PagingArticleRepository pagingArticleRepository;
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
        return articleRepository.findByIdWithAll(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    private boolean isLike(Article article, AppMember appMember) {
        return likeRepository.existsByArticleIdAndMemberId(article.getId(), appMember.getPayload());
    }

    private Long getLikeCount(Article article) {
        return likeRepository.countByArticleId(article.getId());
    }

    private ArticleResponse checkGuest(Article article, List<String> tagNames, AppMember appMember, boolean hasVote,
                                       LikeResponse likeResponse) {
        if (appMember.isGuest()) {
            return ArticleResponse.of(article, tagNames, false, hasVote, likeResponse);
        }
        return ArticleResponse.of(article, tagNames, article.isAuthor(getMember(appMember)), hasVote, likeResponse);
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse getAll(Long cursorId, Long cursorViews, String category, String sortType,
                                      Pageable pageable, AppMember appMember) {
        Slice<ArticlePreviewDto> articles = pagingArticleRepository.findAllByPage(cursorId, cursorViews, category,
                sortType,
                appMember.getPayload(), pageable);
        Map<Long, List<String>> tags = findTagNames(articles);
        List<ArticlePreviewResponse> articleResponses = getArticlePreviewResponses(articles, tags);
        return new ArticlePageResponse(articleResponses, articles.hasNext());
    }

    private Map<Long, List<String>> findTagNames(Slice<ArticlePreviewDto> articles) {
        List<Long> foundArticleIds = getArticleIds(articles);
        return articleRepositoryCustom.findTags(foundArticleIds);
    }

    private List<Long> getArticleIds(Slice<ArticlePreviewDto> articles) {
        return articles.stream()
                .map(ArticlePreviewDto::getId)
                .collect(Collectors.toList());
    }

    private List<ArticlePreviewResponse> getArticlePreviewResponses(Slice<ArticlePreviewDto> articles,
                                                                    Map<Long, List<String>> tags) {
        return articles.stream()
                .map(article -> new ArticlePreviewResponse(article, tags.get(article.getId())))
                .collect(Collectors.toList());
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

    private long getCommentCount(Article article) {
        return commentRepository.countByArticleId(article.getId());
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse searchByText(Long cursorId, Pageable pageable, String searchText, AppMember appMember) {
        if (searchText.isBlank()) {
            return new ArticlePageResponse(new ArrayList<>(), false);
        }
        Slice<ArticlePreviewDto> articles = pagingArticleRepository.searchByContainingText(cursorId, searchText,
                appMember.getPayload(), pageable);
        Map<Long, List<String>> tagNames = findTagNames(articles);
        List<ArticlePreviewResponse> articleResponses = getArticlePreviewResponses(articles, tagNames);
        return new ArticlePageResponse(articleResponses, articles.hasNext());
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse searchByAuthor(Long cursorId, Pageable pageable, String authorName,
                                              AppMember appMember) {
        if (authorName.isBlank()) {
            return new ArticlePageResponse(new ArrayList<>(), false);
        }
        Slice<Article> articles = pagingArticleRepository.searchByAuthor(cursorId, authorName, pageable);

        List<ArticlePreviewResponse> response = createResponse(appMember, articles);
        return new ArticlePageResponse(response, articles.hasNext());
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
                .filter(tagId -> !articleRepositoryCustom.existsArticleByTagId(tagId))
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
        List<Long> deletedTagIds = getDeletedTagIds(article.getTagIds());
        tagService.deleteAll(deletedTagIds);
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse searchByTag(Long cursorId, Pageable pageable, String tagsText, AppMember appMember) {
        Slice<Article> articles = pagingArticleRepository.searchByTag(cursorId, extract(tagsText), pageable);
        List<ArticlePreviewResponse> response = createResponse(appMember, articles);
        return new ArticlePageResponse(response, articles.hasNext());
    }

    private List<String> extract(String tagsText) {
        return Arrays.asList(tagsText.split(","));
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse getAllByLikes(Long cursorId, Long cursorLikes, String category, Pageable pageable,
                                             AppMember appMember) {
        Slice<ArticlePreviewDto> articles = pagingArticleRepository.findAllByLikes(cursorId, cursorLikes, category,
                appMember.getPayload(),
                pageable);
        Map<Long, List<String>> tagNames = findTagNames(articles);
        List<ArticlePreviewResponse> articleResponses = getArticlePreviewResponses(articles, tagNames);
        return new ArticlePageResponse(articleResponses, articles.hasNext());
    }
}
