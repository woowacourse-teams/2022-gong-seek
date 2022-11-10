package com.woowacourse.gongseek.comment.application;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.exception.ArticleNotFoundException;
import com.woowacourse.gongseek.auth.application.dto.AppMember;
import com.woowacourse.gongseek.auth.exception.NotAuthorException;
import com.woowacourse.gongseek.auth.exception.NotMemberException;
import com.woowacourse.gongseek.comment.application.dto.CommentRequest;
import com.woowacourse.gongseek.comment.application.dto.CommentResponse;
import com.woowacourse.gongseek.comment.application.dto.CommentUpdateRequest;
import com.woowacourse.gongseek.comment.application.dto.CommentsResponse;
import com.woowacourse.gongseek.comment.domain.Comment;
import com.woowacourse.gongseek.comment.domain.repository.CommentRepository;
import com.woowacourse.gongseek.comment.exception.CommentNotFoundException;
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
public class CommentService {

    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public void create(AppMember appMember, Long articleId, CommentRequest commentRequest) {
        validateGuest(appMember);
        Member member = getMember(appMember);
        Article article = getArticle(articleId);

        commentRepository.save(commentRequest.toComment(member, article));
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

    private Article getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException(articleId));
    }

    @Transactional(readOnly = true)
    public CommentsResponse getAllByArticleId(AppMember appMember, Long articleId) {
        List<CommentResponse> responses = commentRepository.findAllByArticleIdWithMember(articleId).stream()
                .map(comment -> checkGuest(comment, appMember))
                .collect(Collectors.toList());
        return new CommentsResponse(responses);
    }

    private CommentResponse checkGuest(Comment comment, AppMember appMember) {
        if (appMember.isGuest()) {
            return new CommentResponse(comment, false);
        }
        return new CommentResponse(comment, comment.isAuthor(getMember(appMember)));
    }

    public void update(AppMember appMember, Long commentId, CommentUpdateRequest updateRequest) {
        Comment comment = checkAuthorization(appMember, commentId);
        comment.updateContent(updateRequest.getContent());
    }

    private Comment checkAuthorization(AppMember appMember, Long commentId) {
        validateGuest(appMember);
        Comment comment = getComment(commentId);
        Member member = getMember(appMember);
        validateAuthor(comment, member);
        return comment;
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
    }

    private void validateAuthor(Comment comment, Member member) {
        if (!comment.isAuthor(member)) {
            throw new NotAuthorException(comment.getArticle().getId(), member.getId());
        }
    }

    public void delete(AppMember appMember, Long commentId) {
        Comment comment = checkAuthorization(appMember, commentId);
        commentRepository.delete(comment);
        Article article = comment.getArticle();
    }
}

