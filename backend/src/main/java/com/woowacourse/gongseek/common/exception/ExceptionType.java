package com.woowacourse.gongseek.common.exception;

import com.woowacourse.gongseek.article.exception.ArticleCategoryNotFoundException;
import com.woowacourse.gongseek.article.exception.ArticleContentLengthException;
import com.woowacourse.gongseek.article.exception.ArticleNotFoundException;
import com.woowacourse.gongseek.article.exception.ArticleTitleEmptyException;
import com.woowacourse.gongseek.article.exception.ArticleTitleTooLongException;
import com.woowacourse.gongseek.auth.exception.EmptyTokenException;
import com.woowacourse.gongseek.auth.exception.GithubApiFailException;
import com.woowacourse.gongseek.auth.exception.GithubUserProfileLoadFailException;
import com.woowacourse.gongseek.auth.exception.InvalidTokenException;
import com.woowacourse.gongseek.auth.exception.InvalidTokenTypeException;
import com.woowacourse.gongseek.auth.exception.NoAuthorizationException;
import com.woowacourse.gongseek.auth.exception.NoSuchAuthenticationDataException;
import com.woowacourse.gongseek.comment.exception.CommentEmptyException;
import com.woowacourse.gongseek.comment.exception.CommentTooLongException;
import com.woowacourse.gongseek.comment.exception.CommentNotFoundException;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionType {

    NOT_FOUND_EXCEPTION_TYPE("0000", "해당 에러 타입을 찾을 수 없습니다.", UnSupportedExceptionType.class),

    GITHUB_API_FAIL_EXCEPTION("1001", "Github API에서 Accesstoken을 받는것에 실패했습니다.", GithubApiFailException.class),
    GITHUB_USER_PROFILE_LOAD_FAIL_EXCEPTION("1002", "Github에서 사용자 정보을 받는것에 실패했습니다.",
            GithubUserProfileLoadFailException.class),
    EMPTY_TOKEN_EXCEPTION("1003", "토큰이 존재하지 않습니다.", EmptyTokenException.class),
    INVALID_TOKEN_TYPE_EXCEPTION("1004", "토큰 타입이 올바르지 않습니다.", InvalidTokenTypeException.class),
    INVALID_TOKEN_EXCEPTION("1005", "토큰 타입이 유효하지 않습니다.", InvalidTokenException.class),
    NO_SUCH_AUTHENTICATION_DATA_EXCEPTION("1006", "인증할 수 있는 사용자 데이터가 없습니다.", NoSuchAuthenticationDataException.class),
    NO_AUTHORIZATION_EXCEPTION("1007", "권한이 없습니다.", NoAuthorizationException.class),

    MEMBER_NOT_FOUND_EXCEPTION("2001", "회원이 존재하지 않습니다.", MemberNotFoundException.class),

    ARTICLE_NOT_FOUND_EXCEPTION("3001", "게시글이 존재하지 않습니다.", ArticleNotFoundException.class),
    ARTICLE_TITLE_LENGTH_EXCEPTION("3002", "게시글 제목은 500자를 초과할 수 없습니다.", ArticleTitleTooLongException.class),
    ARTICLE_TITLE_EMPTY_EXCEPTION("3003", "게시글 제목은 비어있을 수 없습니다.", ArticleTitleEmptyException.class),
    ARTICLE_CONTENT_LENGTH_EXCEPTION("3004", "게시글 내용은 10000자를 초과할 수 없습니다.", ArticleContentLengthException.class),
    ARTICLE_CATEGORY_NOT_FOUND_EXCEPTION("3005", "게시글의 카테고리가 존재하지 않습니다.", ArticleCategoryNotFoundException.class),

    COMMENT_NOT_FOUND_EXCEPTION("4001", "댓글이 존재하지 않습니다.", CommentNotFoundException.class),
    COMMENT_EMPTY_EXCEPTION("4002", "댓글은 비어있을 수 없습니다.", CommentEmptyException.class),
    COMMENT_LENGTH_EXCEPTION("4003", "댓글은 10000자를 초과할 수 없습니다.", CommentTooLongException.class),
    ;

    private String errorCode;
    private String message;
    private Class<? extends ApplicationException> type;

    public static ExceptionType from(Class<?> classType) {
        return Arrays.stream(values())
                .filter(it -> it.type.equals(classType))
                .findFirst()
                .orElseThrow(UnSupportedExceptionType::new);
    }
}
