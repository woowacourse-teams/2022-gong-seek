package com.woowacourse.gongseek.member.application;

import com.woowacourse.gongseek.article.domain.repository.ArticleRepository;
import com.woowacourse.gongseek.article.domain.repository.dto.MyPagePreviewDto;
import com.woowacourse.gongseek.auth.application.dto.AppMember;
import com.woowacourse.gongseek.comment.domain.Comment;
import com.woowacourse.gongseek.comment.domain.repository.CommentRepository;
import com.woowacourse.gongseek.member.domain.Member;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import com.woowacourse.gongseek.member.presentation.dto.MemberDto;
import com.woowacourse.gongseek.member.presentation.dto.MemberUpdateRequest;
import com.woowacourse.gongseek.member.presentation.dto.MemberUpdateResponse;
import com.woowacourse.gongseek.member.presentation.dto.MyPageArticlesResponse;
import com.woowacourse.gongseek.member.presentation.dto.MyPageCommentsResponse;
import java.util.List;
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
        List<MyPagePreviewDto> previewArticles = articleRepository.findAllByMemberIdWithCommentCount(member.getId());
        return new MyPageArticlesResponse(previewArticles);
    }

    public MyPageCommentsResponse getComments(AppMember appMember) {
        Member member = getMember(appMember);
        List<Comment> comments = commentRepository.findAllByMemberId(member.getId());
        return MyPageCommentsResponse.from(comments);
    }

    @Transactional
    public MemberUpdateResponse update(AppMember appMember, MemberUpdateRequest request) {
        Member member = getMember(appMember);
        member.updateName(request.getName());
        return new MemberUpdateResponse(member.getName());
    }
}
