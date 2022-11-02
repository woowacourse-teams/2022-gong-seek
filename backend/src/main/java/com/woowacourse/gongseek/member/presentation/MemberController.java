package com.woowacourse.gongseek.member.presentation;

import com.woowacourse.gongseek.auth.application.dto.AppMember;
import com.woowacourse.gongseek.auth.presentation.AuthenticationPrinciple;
import com.woowacourse.gongseek.member.application.MemberService;
import com.woowacourse.gongseek.member.application.dto.MemberDto;
import com.woowacourse.gongseek.member.application.dto.MemberUpdateRequest;
import com.woowacourse.gongseek.member.application.dto.MemberUpdateResponse;
import com.woowacourse.gongseek.member.application.dto.MyPageArticlesResponse;
import com.woowacourse.gongseek.member.application.dto.MyPageCommentsResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/members/me")
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MemberDto> getOne(@AuthenticationPrinciple AppMember appMember) {
        return ResponseEntity.ok(memberService.getOne(appMember));
    }

    @GetMapping("/articles")
    public ResponseEntity<MyPageArticlesResponse> getArticles(@AuthenticationPrinciple AppMember appMember) {
        return ResponseEntity.ok(memberService.getArticles(appMember));
    }

    @GetMapping("/comments")
    public ResponseEntity<MyPageCommentsResponse> getComments(@AuthenticationPrinciple AppMember appMember) {
        return ResponseEntity.ok(memberService.getComments(appMember));
    }

    @PatchMapping
    public ResponseEntity<MemberUpdateResponse> update(@AuthenticationPrinciple AppMember appMember,
                                                       @Valid @RequestBody MemberUpdateRequest updateRequest) {
        return ResponseEntity.ok(memberService.update(appMember, updateRequest));
    }
}
