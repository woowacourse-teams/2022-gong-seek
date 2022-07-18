package com.woowacourse.gongseek.auth.presentation.dto;

public class LoginAppMember extends AppMember {

    public LoginAppMember(Long payload) {
        super(payload);
    }

    @Override
    public boolean isGuest() {
        return false;
    }
}
