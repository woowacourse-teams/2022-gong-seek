package com.woowacourse.gongseek.member.application;

import com.woowacourse.gongseek.article.domain.Article;
import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import com.woowacourse.gongseek.comment.domain.Comment;
import com.woowacourse.gongseek.comment.domain.repository.CommentRepository;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import com.woowacourse.gongseek.member.presentation.dto.MemberDto;
import com.woowacourse.gongseek.member.presentation.dto.MyPageArticleResponse;
import com.woowacourse.gongseek.member.presentation.dto.MyPageArticlesResponse;
import com.woowacourse.gongseek.member.presentation.dto.MyPageCommentResponse;
import com.woowacourse.gongseek.member.presentation.dto.MyPageCommentsResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final Encryptor encryptor;

    public MemberDto getOne(AppMember appMember) {
        Member member = getMember(appMember);
        return new MemberDto(member);
    }

    private Member getMember(AppMember appMember) {
        return memberRepository.findById(appMember.getPayload())
                .orElseThrow(MemberNotFoundException::new);
    }

    public MyPageArticlesResponse getArticles(AppMember appMember) {
        List<Long> memberIds = getMemberIdsIncludeCipherId(appMember);

        List<Article> articles = articleRepository.findAllByMemberIdIn(memberIds);

        List<MyPageArticleResponse> myPageArticleResponses = getMyPageArticleResponses(articles);
        return new MyPageArticlesResponse(myPageArticleResponses);
    }

    private List<Long> getMemberIdsIncludeCipherId(AppMember appMember) {
        Member member = getMember(appMember);
        List<Long> memberIds = new ArrayList<>(List.of(member.getId()));

        String cipherId = encryptor.encrypt(String.valueOf(member.getId()));
        memberRepository.findByGithubId(cipherId)
                .ifPresent(it -> memberIds.add(it.getId()));
        return memberIds;
    }

    private List<MyPageArticleResponse> getMyPageArticleResponses(List<Article> articles) {
        return articles.stream()
                .map(it -> new MyPageArticleResponse(it, commentRepository.countByArticleId(it.getId())))
                .collect(Collectors.toList());
    }

    public MyPageCommentsResponse getComments(AppMember appMember) {
        List<Long> memberIds = getMemberIdsIncludeCipherId(appMember);

        List<Comment> comments = commentRepository.findAllByMemberIdIn(memberIds);

        List<MyPageCommentResponse> myPageCommentResponses = getMyPageCommentResponses(comments);
        return new MyPageCommentsResponse(myPageCommentResponses);
    }

    private List<MyPageCommentResponse> getMyPageCommentResponses(List<Comment> comments) {
        return comments.stream()
                .map(MyPageCommentResponse::new)
                .collect(Collectors.toList());
    }
}
