package com.woowacourse.gongseek.article.application;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.presentation.dto.ArticleAllResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleResponses;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateResponse;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import com.woowacourse.gongseek.comment.domain.repository.CommentRepository;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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

        Article article = articleRepository.save(articleRequest.toEntity(findMember(appMember)));

        return new ArticleIdResponse(article);
    }

    private void validateGuest(AppMember appMember) {
        if (appMember.isGuest()) {
            throw new IllegalArgumentException("권한이 없는 사용자입니다.");
        }
    }

    private Member findMember(AppMember appMember) {
        return memberRepository.findById(appMember.getPayload())
                .orElseThrow(() -> new IllegalStateException("회원이 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public ArticleResponse findOne(AppMember appMember, Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        article.addViews();
        if (appMember.isGuest()) {
            return new ArticleResponse(article, false);
        }
        return new ArticleResponse(article, article.isAuthor(findMember(appMember)));
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

    public ArticleResponses findAll(AppMember appMember, String category, String sort) {


        if (appMember.isGuest()) {
            if (sort.equals("latest")) {
                if (category.equals("total")) {
                    List<Article> articles = articleRepository.findAll(Sort.by(Direction.DESC, "id"));
                    return new ArticleResponses(articles.stream()
                            .map(article -> {
                                int size = commentRepository.findAllByArticleId(article.getId()).size();
                                return new ArticleAllResponse(article, false, size);
                            })
                            .collect(Collectors.toList()));
                }
                List<Article> articles = articleRepository.findAllByCategory(Category.from(category),
                        Sort.by(Direction.DESC, "id"));
                return new ArticleResponses(articles.stream()
                        .map(article -> {
                            int size = commentRepository.findAllByArticleId(article.getId()).size();
                            return new ArticleAllResponse(article, false, size);
                        })
                        .collect(Collectors.toList()));

            } else if (sort.equals("views")) {
                if (category.equals("total")) {
                    List<Article> articles = articleRepository.findAll(Sort.by(Direction.DESC, "id"));
                    return new ArticleResponses(articles.stream()
                            .map(article -> {
                                int size = commentRepository.findAllByArticleId(article.getId()).size();
                                return new ArticleAllResponse(article, false, size);
                            })
                            .collect(Collectors.toList()));
                }
                List<Article> articles = articleRepository.findAllByCategory(Category.from(category),
                        Sort.by(Direction.DESC, "views"));
                return new ArticleResponses(articles.stream()
                        .map(article -> {
                            int size = commentRepository.findAllByArticleId(article.getId()).size();
                            return new ArticleAllResponse(article, false, size);
                        })
                        .collect(Collectors.toList()));
            }
        }
        Member loginMember = findMember(appMember);
        if (sort.equals("latest")) {
            if (category.equals("total")) {
                List<Article> articles = articleRepository.findAll(Sort.by(Direction.DESC, "id"));
                return new ArticleResponses(articles.stream()
                        .map(article -> {
                            int size = commentRepository.findAllByArticleId(article.getId()).size();
                            return new ArticleAllResponse(article, false, size);
                        })
                        .collect(Collectors.toList()));
            }
            List<Article> articles = articleRepository.findAllByCategory(Category.from(category),
                    Sort.by(Direction.DESC, "id"));
            return new ArticleResponses(articles.stream()
                    .map(article -> {
                        int size = commentRepository.findAllByArticleId(article.getId()).size();
                        return new ArticleAllResponse(article, false, size);
                    })
                    .collect(Collectors.toList()));

        } else if (sort.equals("views")) {
            if (category.equals("total")) {
                List<Article> articles = articleRepository.findAll(Sort.by(Direction.DESC, "id"));
                return new ArticleResponses(articles.stream()
                        .map(article -> {
                            int size = commentRepository.findAllByArticleId(article.getId()).size();
                            return new ArticleAllResponse(article, false, size);
                        })
                        .collect(Collectors.toList()));
            }
            List<Article> articles = articleRepository.findAllByCategory(Category.from(category),
                    Sort.by(Direction.DESC, "views"));
            return new ArticleResponses(articles.stream()
                    .map(article -> {
                        int size = commentRepository.findAllByArticleId(article.getId()).size();
                        return new ArticleAllResponse(article, false, size);
                    })
                    .collect(Collectors.toList()));
        }

        throw new IllegalArgumentException("정렬 기준이 잘못 들어왔습니다.");
    }

    private Article checkAuthorization(AppMember appMember, Long id) {
        validateGuest(appMember);
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        Member member = memberRepository.findById(appMember.getPayload())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        validateAuthor(article, member);
        return article;
    }

    private void validateAuthor(Article article, Member member) {
        if (!article.isAuthor(member)) {
            throw new IllegalStateException("작성자만 권한이 있습니다.");
        }
    }
}
