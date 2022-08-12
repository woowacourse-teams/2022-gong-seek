package com.woowacourse.gongseek.member.exception;

import com.woowacourse.gongseek.common.exception.NotFoundException;

public class MemberNotFoundException extends NotFoundException {

    public MemberNotFoundException(long memberId) {
        super(String.format("(memberId : %d)", memberId));
    }
}
