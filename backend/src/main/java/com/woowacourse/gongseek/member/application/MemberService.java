package com.woowacourse.gongseek.member.application;

import com.woowacourse.gongseek.article.domain.repository.ArticleRepositoryCustom;
import com.woowacourse.gongseek.article.domain.repository.dto.MyPageArticleDto;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import com.woowacourse.gongseek.comment.domain.Comment;
import com.woowacourse.gongseek.comment.domain.repository.CommentRepository;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import com.woowacourse.gongseek.member.presentation.dto.MemberDto;
import com.woowacourse.gongseek.member.presentation.dto.MemberUpdateRequest;
import com.woowacourse.gongseek.member.presentation.dto.MemberUpdateResponse;
import com.woowacourse.gongseek.member.presentation.dto.MyPageArticlesResponse;
import com.woowacourse.gongseek.member.presentation.dto.MyPageCommentResponse;
import com.woowacourse.gongseek.member.presentation.dto.MyPageCommentsResponse;
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
    private final ArticleRepositoryCustom articleRepositoryCustom;
    private final CommentRepository commentRepository;

    public MemberDto getOne(AppMember appMember) {
        Member member = getMember(appMember);
        return new MemberDto(member);
    }

    private Member getMember(AppMember appMember) {
        return memberRepository.findById(appMember.getPayload())
                .orElseThrow(() -> new MemberNotFoundException(appMember.getPayload()));
    }

    public MyPageArticlesResponse getArticles(AppMember appMember) {
        Member member = getMember(appMember);
        List<MyPageArticleDto> myPageArticles = articleRepositoryCustom.findAllByMemberIdWithCommentCount(
                member.getId());
        return new MyPageArticlesResponse(myPageArticles);
    }

    public MyPageCommentsResponse getComments(AppMember appMember) {
        Member member = getMember(appMember);
        List<Comment> comments = commentRepository.findAllByMemberId(member.getId());

        List<MyPageCommentResponse> myPageCommentResponses = getMyPageCommentResponses(comments);
        return new MyPageCommentsResponse(myPageCommentResponses);
    }

    private List<MyPageCommentResponse> getMyPageCommentResponses(List<Comment> comments) {
        return comments.stream()
                .map(MyPageCommentResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public MemberUpdateResponse update(AppMember appMember, MemberUpdateRequest request) {
        Member member = getMember(appMember);
        member.updateName(request.getName());
        return new MemberUpdateResponse(member.getName());
    }
}
