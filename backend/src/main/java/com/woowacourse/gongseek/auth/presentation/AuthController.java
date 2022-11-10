package com.woowacourse.gongseek.auth.presentation;

import com.woowacourse.gongseek.auth.application.AuthService;
import com.woowacourse.gongseek.auth.application.OAuthClient;
import com.woowacourse.gongseek.auth.application.dto.AccessTokenResponse;
import com.woowacourse.gongseek.auth.application.dto.GithubProfileResponse;
import com.woowacourse.gongseek.auth.application.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.application.dto.OAuthLoginUrlResponse;
import com.woowacourse.gongseek.auth.application.dto.TokenResponse;
import com.woowacourse.gongseek.auth.utils.CookieUtils;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final OAuthClient githubOAuthClient;
    private final AuthService authService;

    @GetMapping("/github")
    public ResponseEntity<OAuthLoginUrlResponse> transferLoginUrl() {
        return ResponseEntity.ok(new OAuthLoginUrlResponse(githubOAuthClient.getRedirectUrl()));
    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@Valid @RequestBody OAuthCodeRequest OAuthCodeRequest,
                                                     HttpServletResponse httpServletResponse) {
        GithubProfileResponse githubProfile = githubOAuthClient.getMemberProfile(OAuthCodeRequest.getCode());
        TokenResponse tokenResponse = authService.generateToken(githubProfile);
        ResponseCookie cookie = CookieUtils.create(tokenResponse.getRefreshToken());
        httpServletResponse.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(new AccessTokenResponse(tokenResponse.getAccessToken()));
    }

    @GetMapping("/refresh")
    public ResponseEntity<AccessTokenResponse> renew(
            @CookieValue(value = "refreshToken", required = false) UUID refreshToken,
            HttpServletResponse httpServletResponse
    ) {
        TokenResponse tokenResponse = authService.renewToken(refreshToken);
        ResponseCookie newCookie = CookieUtils.create(tokenResponse.getRefreshToken());
        httpServletResponse.setHeader(HttpHeaders.SET_COOKIE, newCookie.toString());

        return ResponseEntity.ok(new AccessTokenResponse(tokenResponse.getAccessToken()));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(value = "refreshToken", required = false) UUID refreshToken,
            HttpServletResponse httpServletResponse
    ) {
        authService.updateRefreshToken(refreshToken);
        ResponseCookie deleteCookie = CookieUtils.delete();
        httpServletResponse.setHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());

        return ResponseEntity.noContent().build();
    }
}
