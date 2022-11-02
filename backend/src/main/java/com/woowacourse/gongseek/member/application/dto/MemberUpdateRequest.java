package com.woowacourse.gongseek.member.application.dto;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberUpdateRequest {

    @NotNull
    @Length(min = 1, message = "수정할 이름의 길이는 1이상이어야 합니다.")
    private String name;
}
