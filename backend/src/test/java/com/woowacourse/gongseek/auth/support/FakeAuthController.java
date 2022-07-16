package com.woowacourse.gongseek.auth.support;

import com.woowacourse.gongseek.auth.application.AuthService;
import com.woowacourse.gongseek.auth.infra.JwtTokenProvider;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.member.domain.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@TestConstructor(autowireMode = AutowireMode.ALL)
@RestController
public class FakeAuthController {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    public FakeAuthController(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authService = new AuthService(new FakeAuthClient(), memberRepository, jwtTokenProvider);
    }

    @PostMapping("/fake/token")
    public ResponseEntity<TokenResponse> login(@RequestBody OAuthCodeRequest OAuthCodeRequest) {
        TokenResponse token = authService.generateAccessToken(OAuthCodeRequest);
        return ResponseEntity.ok(token);
    }
}
