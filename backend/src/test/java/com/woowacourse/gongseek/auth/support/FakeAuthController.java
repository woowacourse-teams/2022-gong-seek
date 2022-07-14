package com.woowacourse.gongseek.auth.support;

import com.woowacourse.gongseek.auth.presentation.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class FakeAuthController {

    @PostMapping("/fake/token")
    public ResponseEntity<TokenResponse> login(@RequestBody OAuthCodeRequest OAuthCodeRequest) {
        String token = GithubClientFixtures.generateAccessToken(OAuthCodeRequest.getCode());
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
