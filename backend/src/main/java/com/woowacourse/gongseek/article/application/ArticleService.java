package com.woowacourse.gongseek.article.application;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticleDto;
import com.woowacourse.gongseek.article.domain.repository.dto.ArticlePreviewDto;
import com.woowacourse.gongseek.article.exception.ArticleNotFoundException;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticlePageResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticlePreviewResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateResponse;
import com.woowacourse.gongseek.auth.exception.NotAuthorException;
import com.woowacourse.gongseek.auth.exception.NotMemberException;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import com.woowacourse.gongseek.tag.application.TagService;
import com.woowacourse.gongseek.tag.domain.Tags;
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
    private final TempArticleService tempArticleService;
    private final MemberRepository memberRepository;
    private final TagService tagService;

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
        return articleRepository.findByIdWithAll(id, appMember.getPayload())
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse getAll(Long cursorId, Integer cursorViews, String category, String sortType,
                                      Pageable pageable, AppMember appMember) {
        Slice<ArticlePreviewDto> articles = articleRepository.findAllByPage(cursorId, cursorViews, category, sortType,
                appMember.getPayload(), pageable);
        Map<Long, List<String>> tags = findByTags(articles);
        List<ArticlePreviewResponse> articleResponses = getArticlePreviewResponses(articles, tags);
        return new ArticlePageResponse(articleResponses, articles.hasNext());
    }

    private List<ArticlePreviewResponse> getArticlePreviewResponses(Slice<ArticlePreviewDto> articles,
                                                                    Map<Long, List<String>> tags) {
        return articles.stream()
                .map(article -> new ArticlePreviewResponse(article, tags.get(article.getId())))
                .collect(Collectors.toList());
    }

    private Map<Long, List<String>> findByTags(Slice<ArticlePreviewDto> articles) {
        List<Long> foundArticleIds = getArticleIds(articles);
        return articleRepository.findTags(foundArticleIds);
    }

    private List<Long> getArticleIds(Slice<ArticlePreviewDto> articles) {
        return articles.stream()
                .map(ArticlePreviewDto::getId)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse getAllByLikes(Long cursorId, Long likes, String category, Pageable pageable,
                                             AppMember appMember) {
        Slice<ArticlePreviewDto> articles = articleRepository.findAllByLikes(cursorId, likes, category,
                appMember.getPayload(),
                pageable);
        Map<Long, List<String>> tags = findByTags(articles);
        List<ArticlePreviewResponse> articleResponses = getArticlePreviewResponses(articles, tags);
        return new ArticlePageResponse(articleResponses, articles.hasNext());
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse searchByText(Long cursorId, Pageable pageable, String searchText,
                                            AppMember appMember) {
        if (searchText.isBlank()) {
            return new ArticlePageResponse(new ArrayList<>(), false);
        }
        Slice<ArticlePreviewDto> articles = articleRepository.searchByContainingText(cursorId, searchText,
                appMember.getPayload(), pageable);
        Map<Long, List<String>> tags = findByTags(articles);
        List<ArticlePreviewResponse> articleResponses = getArticlePreviewResponses(articles, tags);
        return new ArticlePageResponse(articleResponses, articles.hasNext());
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse searchByAuthor(Long cursorId, Pageable pageable, String authorName,
                                              AppMember appMember) {
        if (authorName.isBlank()) {
            return new ArticlePageResponse(new ArrayList<>(), false);
        }
        Slice<ArticlePreviewDto> articles = articleRepository.searchByAuthor(cursorId, authorName,
                appMember.getPayload(), pageable);
        Map<Long, List<String>> tags = findByTags(articles);
        List<ArticlePreviewResponse> articleResponses = getArticlePreviewResponses(articles, tags);
        return new ArticlePageResponse(articleResponses, articles.hasNext());
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse searchByTag(Long cursorId, Pageable pageable, String tagsText, AppMember appMember) {
        Slice<ArticlePreviewDto> articles = articleRepository.searchByTag(cursorId, appMember.getPayload(),
                extract(tagsText), pageable);
        Map<Long, List<String>> tags = findByTags(articles);
        List<ArticlePreviewResponse> articleResponses = getArticlePreviewResponses(articles, tags);
        return new ArticlePageResponse(articleResponses, articles.hasNext());
    }

    private List<String> extract(String tagsText) {
        return Arrays.asList(tagsText.split(","));
    }

    public ArticleUpdateResponse update(AppMember appMember, ArticleUpdateRequest articleUpdateRequest, Long id) {
        Article article = checkAuthorization(appMember, id);
        List<Long> existingTagIds = article.getTagIds();
        List<String> updatedTagNames = articleUpdateRequest.getTag();
        Tags foundTags = tagService.getOrCreateTags(Tags.from(updatedTagNames));
        article.update(articleUpdateRequest.getTitle(), articleUpdateRequest.getContent());
        article.updateTag(foundTags);
        deleteUnusedTags(existingTagIds, foundTags.getTagIds());

        return new ArticleUpdateResponse(article);
    }

    private void deleteUnusedTags(List<Long> existingTagIds, List<Long> updatedTagIds) {
//        List<Long> deletedTagIds = new ArrayList<>(existingTagIds);
//        deletedTagIds.removeAll(updatedTagIds);
        existingTagIds.removeAll(updatedTagIds);
        List<Long> deletedTagIds = getDeletedTagIds(existingTagIds);
        tagService.deleteAll(deletedTagIds);
    }

    private List<Long> getDeletedTagIds(List<Long> tagIds) {
        return tagIds.stream()
                .filter(tagId -> !articleRepository.existsArticleByTagId(tagId))
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

    private Article getArticle(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    public void delete(AppMember appMember, Long id) {
        Article article = checkAuthorization(appMember, id);
        articleRepository.delete(article);
        List<Long> deletedTagIds = getDeletedTagIds(article.getTagIds());
        tagService.deleteAll(deletedTagIds);
    }
}
