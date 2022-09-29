package com.woowacourse.gongseek.article.application;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticleDto;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticlePreviewDto;
import com.woowacourse.gongseek.article.exception.ArticleNotFoundException;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticlePageResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticlePageResponseNew;
import com.woowacourse.gongseek.article.presentation.dto.ArticlePreviewResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
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
    private final TagService tagService;
    private final LikeRepository likeRepository;
    private final EntityManager entityManager;

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

    public ArticleDto getOne(AppMember appMember, Long id) {
        articleRepository.addViews(id);
        ArticleDto articleDto = getArticleDto(id, appMember);

        entityManager.flush();

        return articleDto;
    }

    private ArticleDto getArticleDto(Long id, AppMember appMember) {
        return articleRepository.findByIdWithAll(id, appMember.getPayload())
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    private Article getArticle(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    private boolean isLike(Article article, AppMember appMember) {
        return likeRepository.existsByArticleIdAndMemberId(article.getId(), appMember.getPayload());
    }

    private Long getLikeCount(Article article) {
        return likeRepository.countByArticleId(article.getId());
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
    public ArticlePageResponseNew searchByText(Long cursorId, Pageable pageable, String searchText,
                                               AppMember appMember) {
        if (searchText.isBlank()) {
            return new ArticlePageResponseNew(new ArrayList<>(), false);
        }
        Slice<ArticlePreviewDto> articles = articleRepository.searchByContainingText(cursorId, searchText,
                appMember.getPayload(), pageable);

        return new ArticlePageResponseNew(articles.getContent(), articles.hasNext());
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
        List<Long> existingTags = article.getTagIds();
        List<String> updatedTagNames = articleUpdateRequest.getTag();
        Tags tags = Tags.from(updatedTagNames);
        Tags foundTags = tagService.getOrCreateTags(tags);
        article.update(articleUpdateRequest.getTitle(), articleUpdateRequest.getContent());
        article.updateTag(foundTags);
        deleteUnusedTags(existingTags, foundTags.getTagIds());

        return new ArticleUpdateResponse(article);
    }

    private void deleteUnusedTags(List<Long> existingTagIds, List<Long> updatedTagIds) {
        List<Long> deletedTagIds = new ArrayList<>(existingTagIds);
        deletedTagIds.removeAll(updatedTagIds);
        tagService.deleteAll(deletedTagIds);
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

    private List<Long> getDeletedTagIds(List<Long> tagIds) {
        return tagIds.stream()
                .filter(tagId -> !articleRepository.existsArticleByTagId(tagId))
                .collect(Collectors.toList());
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
