package com.woowacourse.gongseek.member.presentation;

import com.woowacourse.gongseek.auth.presentation.AuthenticationPrinciple;
import com.woowacourse.gongseek.auth.presentation.dto.AppMember;
import com.woowacourse.gongseek.member.application.MemberService;
import com.woowacourse.gongseek.member.presentation.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/api/members/me")
    private ResponseEntity<MemberDto> getOne(@AuthenticationPrinciple AppMember appMember) {
        return ResponseEntity.ok(memberService.getOne(appMember));
    }
}
