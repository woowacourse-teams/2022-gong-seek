package com.woowacourse.gongseek.auth.presentation;

import com.woowacourse.gongseek.auth.application.AuthService;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthLoginUrlResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @GetMapping("/github")
    public ResponseEntity<OAuthLoginUrlResponse> transferLoginUrl() {
        return ResponseEntity.ok(authService.getLoginUrl());
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody OAuthCodeRequest OAuthCodeRequest) {
        return ResponseEntity.ok(authService.generateAccessToken(OAuthCodeRequest));
    }
}
