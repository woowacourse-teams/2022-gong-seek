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
        Member member = findMember(appMember);
        Article article = findArticle(articleId);
        Comment comment = new Comment(commentRequest.getContent(), member, article);

        commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public CommentsResponse findByArticleId(AppMember appMember, Long articleId) {
        List<CommentResponse> responses = commentRepository.findAllByArticleId(articleId).stream()
                .map(comment -> CommentResponse.of(comment, isAuthor(appMember, comment)))
                .collect(Collectors.toList());
        return new CommentsResponse(responses);
    }

    private boolean isAuthor(AppMember appMember, Comment comment) {
        if (appMember.isGuest()) {
            return false;
        }
        Member member = findMember(appMember);
        return comment.isAuthor(member);
    }

    public void update(AppMember appMember, Long commentId, CommentRequest updateRequest) {
        validateGuest(appMember);
        Member member = findMember(appMember);
        Comment comment = findComment(commentId);

        if (!comment.isAuthor(member)) {
            throw new IllegalArgumentException("????????? ????????? ????????? ????????? ??? ????????????.");
        }
        comment.updateContent(updateRequest.getContent());
    }

    public void delete(AppMember appMember, Long commentId) {
        validateGuest(appMember);
        Member member = findMember(appMember);
        Comment comment = findComment(commentId);

        if (!comment.isAuthor(member)) {
            throw new IllegalArgumentException("????????? ????????? ????????? ????????? ??? ????????????.");
        }
        commentRepository.delete(comment);
    }

    private void validateGuest(AppMember appMember) {
        if (appMember.isGuest()) {
            throw new IllegalArgumentException("????????? ?????? ??????????????????.");
        }
    }

    private Member findMember(AppMember appMember) {
        return memberRepository.findById(appMember.getPayload())
                .orElseThrow(() -> new IllegalStateException("????????? ???????????? ????????????."));
    }

    private Article findArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalStateException("???????????? ???????????? ????????????."));
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("????????? ???????????? ????????????."));
    }
}

