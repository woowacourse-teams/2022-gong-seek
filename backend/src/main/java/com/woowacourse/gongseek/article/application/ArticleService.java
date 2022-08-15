package com.woowacourse.gongseek.article.application;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.articletag.ArticleTag;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.domain.repository.ArticleTagRepository;
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
import com.woowacourse.gongseek.member.application.Encryptor;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import com.woowacourse.gongseek.tag.domain.Name;
import com.woowacourse.gongseek.tag.domain.Tag;
import com.woowacourse.gongseek.tag.domain.Tags;
import com.woowacourse.gongseek.tag.domain.repository.TagRepository;
import com.woowacourse.gongseek.vote.domain.repository.VoteRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private static final String ANONYMOUS_AVATAR_URL = "https://raw.githubusercontent.com/woowacourse-teams/2022-gong-seek/develop/frontend/src/assets/gongseek.png";
    private static final String ANONYMOUS_NAME = "익명";

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final VoteRepository voteRepository;
    private final TagRepository tagRepository;
    private final ArticleTagRepository articleTagRepository;
    private final LikeRepository likeRepository;
    private final Encryptor encryptor;

    public ArticleIdResponse save(AppMember appMember, ArticleRequest articleRequest) {
        validateGuest(appMember);
        Member member = getAuthor(appMember, articleRequest);

        Tags tags = new Tags(getTags(articleRequest.getTag()));
        List<Tag> foundTags = getTags(tags);

        Article article = articleRepository.save(articleRequest.toEntity(member));
        foundTags.forEach(tag -> articleTagRepository.save(new ArticleTag(article, tag)));

        return new ArticleIdResponse(article);
    }

    private void validateGuest(AppMember appMember) {
        if (appMember.isGuest()) {
            throw new NotMemberException();
        }
    }

    private Member getAuthor(AppMember appMember, ArticleRequest articleRequest) {
        if (articleRequest.getIsAnonymous()) {
            String cipherId = encryptor.encrypt(String.valueOf(appMember.getPayload()));
            return memberRepository.findByGithubId(cipherId)
                    .orElseGet(() -> memberRepository.save(new Member(ANONYMOUS_NAME, cipherId, ANONYMOUS_AVATAR_URL)));
        }
        return getMember(appMember);
    }

    public List<Tag> getTags(List<String> tag) {
        return tag.stream()
                .map(Tag::new)
                .collect(Collectors.toList());
    }

    private List<Tag> getTags(Tags tags) {
        return tags.getTagNames().stream()
                .map(this::getOrCreateTagIfNotExist)
                .collect(Collectors.toList());
    }

    private Tag getOrCreateTagIfNotExist(String name) {
        return tagRepository.findByName(new Name(name))
                .orElseGet(() -> tagRepository.save(new Tag(name)));
    }

    private Member getMember(AppMember appMember) {
        return memberRepository.findById(appMember.getPayload())
                .orElseThrow(() -> new MemberNotFoundException(appMember.getPayload()));
    }

    public ArticleResponse getOne(AppMember appMember, Long id) {
        Article article = getArticle(id);

        List<String> tagNames = getTagNames(article);

        article.addViews();

        boolean hasVote = voteRepository.existsByArticleId(article.getId());
        LikeResponse likeResponse = new LikeResponse(isLike(article, appMember), getLikeCount(article));

        return checkGuest(article, tagNames, appMember, hasVote, likeResponse);
    }

    private Article getArticle(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    private List<String> getTagNames(Article article) {
        List<ArticleTag> articleTags = articleTagRepository.findAllByArticleId(article.getId());
        return articleTags.stream()
                .map(articleTag -> articleTag.getTag().getName().getValue())
                .collect(Collectors.toList());
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
            return new ArticleResponse(article, tagNames, false, hasVote, likeResponse);
        }
        return checkAuthor(article, tagNames, getMember(appMember), hasVote, likeResponse);
    }

    private ArticleResponse checkAuthor(Article article, List<String> tagNames, Member member, boolean hasVote,
                                        LikeResponse likeResponse) {
        if (article.isAnonymous()) {
            String cipherId = encryptor.encrypt(String.valueOf(member.getId()));
            return new ArticleResponse(article, tagNames, article.isAnonymousAuthor(cipherId), hasVote, likeResponse);
        }
        return new ArticleResponse(article, tagNames, article.isAuthor(member), hasVote, likeResponse);
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse getAll(Long cursorId, Integer cursorViews, String category, String sortType,
                                      int pageSize, AppMember appMember) {
        List<ArticlePreviewResponse> articles = getAllByPage(cursorId, cursorViews, category, sortType, pageSize,
                appMember);

        return getArticlePageResponse(articles, pageSize);
    }

    private List<ArticlePreviewResponse> getAllByPage(Long cursorId, Integer cursorViews, String category,
                                                      String sortType, int pageSize, AppMember appMember) {
        return articleRepository.findAllByPage(cursorId, cursorViews, category, sortType, pageSize)
                .stream()
                .map(it -> getArticlePreviewResponse(it, appMember))
                .collect(Collectors.toList());
    }

    private ArticlePreviewResponse getArticlePreviewResponse(Article article, AppMember appMember) {
        List<String> tagNames = getTagNames(article);
        return ArticlePreviewResponse.of(article, tagNames, getCommentCount(article),
                new LikeResponse(isLike(article, appMember), getLikeCount(article)));
    }

    private int getCommentCount(Article article) {
        return commentRepository.countByArticleId(article.getId());
    }

    private ArticlePageResponse getArticlePageResponse(List<ArticlePreviewResponse> articles, int pageSize) {
        if (articles.size() == pageSize + 1) {
            return new ArticlePageResponse(articles.subList(0, pageSize), true);
        }
        return new ArticlePageResponse(articles, false);
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse search(Long cursorId, int pageSize, String searchText,
                                      AppMember appMember) {
        if (searchText.isBlank()) {
            return new ArticlePageResponse(new ArrayList<>(), false);
        }
        List<ArticlePreviewResponse> articles = searchByText(cursorId, pageSize, searchText, appMember);

        return getArticlePageResponse(articles, pageSize);
    }

    private List<ArticlePreviewResponse> searchByText(Long cursorId, int pageSize, String searchText,
                                                      AppMember appMember) {
        return articleRepository.searchByContainingText(cursorId, pageSize, searchText)
                .stream()
                .map(it -> getArticlePreviewResponse(it, appMember))
                .collect(Collectors.toList());
    }

    public ArticleUpdateResponse update(AppMember appMember, ArticleUpdateRequest articleUpdateRequest, Long id) {
        Tags tags = new Tags(getTags(articleUpdateRequest.getTag()));
        List<Tag> foundTags = getTags(tags);

        Article article = checkAuthorization(appMember, id);
        article.update(articleUpdateRequest.getTitle(), articleUpdateRequest.getContent());

        articleTagRepository.deleteAllByArticleId(article.getId());
        foundTags.forEach(tag -> articleTagRepository.save(new ArticleTag(article, tag)));

        return new ArticleUpdateResponse(article);
    }

    private Article checkAuthorization(AppMember appMember, Long id) {
        validateGuest(appMember);
        Member member = getMember(appMember);
        Article article = getArticle(id);
        validateAuthor(article, member);
        return article;
    }

    private void validateAuthor(Article article, Member member) {
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

    public void delete(AppMember appMember, Long id) {
        Article article = checkAuthorization(appMember, id);
        articleRepository.delete(article);
    }
}
