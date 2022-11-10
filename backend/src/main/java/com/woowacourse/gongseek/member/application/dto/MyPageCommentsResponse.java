package com.woowacourse.gongseek.member.application.dto;

import com.woowacourse.gongseek.comment.domain.Comment;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MyPageCommentsResponse {

    private List<MyPageCommentResponse> comments;

    public static MyPageCommentsResponse from(List<Comment> comments) {
        List<MyPageCommentResponse> myPageCommentResponses = comments.stream()
                .map(MyPageCommentResponse::new)
                .collect(Collectors.toList());
        return new MyPageCommentsResponse(myPageCommentResponses);
    }
}
