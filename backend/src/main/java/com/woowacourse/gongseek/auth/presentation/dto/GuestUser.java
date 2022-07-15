package com.woowacourse.gongseek.auth.presentation.dto;

public class GuestUser extends User {
    public GuestUser() {
        super(0L);
    }

    @Override
    public boolean isGuest() {
        return true;
    }
}
