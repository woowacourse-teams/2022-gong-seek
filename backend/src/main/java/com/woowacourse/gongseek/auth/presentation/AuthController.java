package com.woowacourse.gongseek.auth.presentation;

import com.woowacourse.gongseek.auth.application.AuthService;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthLoginUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
