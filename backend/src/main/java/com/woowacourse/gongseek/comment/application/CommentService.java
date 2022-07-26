package com.woowacourse.gongseek.comment.application;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import com.woowacourse.gongseek.comment.domain.Comment;
import com.woowacourse.gongseek.comment.domain.repository.CommentRepository;
import com.woowacourse.gongseek.comment.presentation.dto.CommentRequest;
import com.woowacourse.gongseek.comment.presentation.dto.CommentResponse;
import com.woowacourse.gongseek.comment.presentation.dto.CommentsResponse;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
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

    public void save(AppMember appMember, Long articleId, CommentRequest commentRequest) {
        validateGuest(appMember);
        Member member = getMember(appMember);
        Article article = getArticle(articleId);
        Comment comment = new Comment(commentRequest.getContent(), member, article);

        commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public CommentsResponse getAll(AppMember appMember, Long articleId) {
        List<CommentResponse> responses = commentRepository.findAllByArticleId(articleId).stream()
                .map(comment -> CommentResponse.of(comment, isAuthor(appMember, comment)))
                .collect(Collectors.toList());
        return new CommentsResponse(responses);
    }

    private boolean isAuthor(AppMember appMember, Comment comment) {
        if (appMember.isGuest()) {
            return false;
        }
        Member member = getMember(appMember);
        return comment.isAuthor(member);
    }

    public void update(AppMember appMember, Long commentId, CommentRequest updateRequest) {
        Comment comment = checkAuthorization(appMember, commentId);
        comment.updateContent(updateRequest.getContent());
    }

    public void delete(AppMember appMember, Long commentId) {
        validateGuest(appMember);
        Member member = getMember(appMember);
        Comment comment = getComment(commentId);

        validateAuthor(member, comment);
        commentRepository.delete(comment);
    }

    private void validateGuest(AppMember appMember) {
        if (appMember.isGuest()) {
            throw new IllegalArgumentException("권한이 없는 사용자입니다.");
        }
    }

    private Member getMember(AppMember appMember) {
        return memberRepository.findById(appMember.getPayload())
                .orElseThrow(() -> new IllegalStateException("회원이 존재하지 않습니다."));
    }

    private Article getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalStateException("게시글이 존재하지 않습니다."));
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("댓글이 존재하지 않습니다."));
    }

    private Comment checkAuthorization(AppMember appMember, Long commentId) {
        validateGuest(appMember);
        Comment comment = getComment(commentId);
        Member member = getMember(appMember);
        validateAuthor(member, comment);
        return comment;
    }

    private void validateAuthor(Member member, Comment comment) {
        if (!comment.isAuthor(member)) {
            throw new IllegalArgumentException("댓글 작성자만 권한이 있습니다.");
        }
    }
}

