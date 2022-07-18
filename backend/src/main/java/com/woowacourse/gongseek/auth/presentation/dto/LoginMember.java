package com.woowacourse.gongseek.auth.presentation.dto;

public class LoginMember extends AppMember {

    public LoginMember(Long payload) {
        super(payload);
    }

    @Override
    public boolean isGuest() {
        return false;
    }
}
