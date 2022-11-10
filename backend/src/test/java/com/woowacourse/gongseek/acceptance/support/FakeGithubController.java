package com.woowacourse.gongseek.acceptance.support;

import com.woowacourse.gongseek.auth.application.dto.GithubAccessTokenRequest;
import com.woowacourse.gongseek.auth.application.dto.GithubAccessTokenResponse;
import com.woowacourse.gongseek.auth.application.dto.GithubProfileResponse;
import com.woowacourse.gongseek.auth.support.GithubClientFixtures;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class FakeGithubController {

    @PostMapping("/login/oauth/access_token")
    public ResponseEntity<GithubAccessTokenResponse> getAccessToken(
            @RequestBody GithubAccessTokenRequest tokenRequest) {
        try {
            return ResponseEntity.ok(GithubClientFixtures.getAccessToken(tokenRequest.getCode()));
        } catch (IllegalStateException e) {
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<GithubProfileResponse> getProfile(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String accessToken) {
        String token = accessToken.replaceAll("token ", "");
        try {
            return ResponseEntity.ok(GithubClientFixtures.getGithubProfileByToken(token));
        } catch (IllegalStateException e) {
            return ResponseEntity.ok(null);
        }
    }
}
