package com.woowacourse.gongseek.auth.presentation.dto;

public class LoginUser extends User {

    public LoginUser(Long payload) {
        super(payload);
    }

    @Override
    public boolean isGuest() {
        return false;
    }
}
