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
import com.woowacourse.gongseek.auth.exception.NoAuthorizationException;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import com.woowacourse.gongseek.comment.domain.repository.CommentRepository;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    public ArticleIdResponse save(AppMember appMember, ArticleRequest articleRequest) {
        validateGuest(appMember);
        Article article = articleRepository.save(articleRequest.toEntity(getMember(appMember)));

        return new ArticleIdResponse(article);
    }

    private void validateGuest(AppMember appMember) {
        if (appMember.isGuest()) {
            throw new NoAuthorizationException();
        }
    }

    public ArticleResponse getOne(AppMember appMember, Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(ArticleNotFoundException::new);
        article.addViews();
        if (appMember.isGuest()) {
            return new ArticleResponse(article, false);
        }
        return new ArticleResponse(article, article.isAuthor(getMember(appMember)));
    }

    @Transactional(readOnly = true)
    public ArticlePageResponse getArticles(Long cursorId, Integer cursorViews, String category, String sortType,
                                           int size) {
        List<ArticlePreviewResponse> articles = articleRepository.findAllByPage(cursorId, cursorViews, category,
                        sortType, size).stream()
                .map(article -> ArticlePreviewResponse.of(article, getCommentCount(article)))
                .collect(Collectors.toList());

        if (articles.size() == size + 1) {
            return new ArticlePageResponse(articles.subList(0, size), true);
        }
        return new ArticlePageResponse(articles, false);
    }

    private int getCommentCount(Article article) {
        return commentRepository.countByArticleId(article.getId());
    }

    public ArticleUpdateResponse update(AppMember appMember, ArticleUpdateRequest articleUpdateRequest, Long id) {
        Article article = checkAuthorization(appMember, id);
        article.update(articleUpdateRequest.getTitle(), articleUpdateRequest.getContent());

        return new ArticleUpdateResponse(article);
    }

    public void delete(AppMember appMember, Long id) {
        Article article = checkAuthorization(appMember, id);
        articleRepository.delete(article);
    }

    private Article checkAuthorization(AppMember appMember, Long id) {
        validateGuest(appMember);
        Article article = getArticle(id);
        Member member = getMember(appMember);
        validateAuthor(article, member);
        return article;
    }

    private void validateAuthor(Article article, Member member) {
        if (!article.isAuthor(member)) {
            throw new NoAuthorizationException();
        }
    }

    private Member getMember(AppMember appMember) {
        return memberRepository.findById(appMember.getPayload())
                .orElseThrow(MemberNotFoundException::new);
    }

    private Article getArticle(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(ArticleNotFoundException::new);
    }
}
