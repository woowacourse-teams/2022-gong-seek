package com.woowacourse.gongseek.common.exception;

import com.woowacourse.gongseek.article.exception.ArticleCategoryNotFoundException;
import com.woowacourse.gongseek.article.exception.ArticleContentNullOrEmptyException;
import com.woowacourse.gongseek.article.exception.ArticleContentTooLongException;
import com.woowacourse.gongseek.article.exception.ArticleNotFoundException;
import com.woowacourse.gongseek.article.exception.ArticleTitleNullOrEmptyException;
import com.woowacourse.gongseek.article.exception.ArticleTitleTooLongException;
import com.woowacourse.gongseek.article.exception.DuplicateTagException;
import com.woowacourse.gongseek.auth.exception.EmptyTokenException;
import com.woowacourse.gongseek.auth.exception.GithubAccessTokenLoadFailException;
import com.woowacourse.gongseek.auth.exception.GithubUserProfileLoadFailException;
import com.woowacourse.gongseek.auth.exception.InvalidTokenException;
import com.woowacourse.gongseek.auth.exception.InvalidTokenTypeException;
import com.woowacourse.gongseek.auth.exception.NoAuthorizationException;
import com.woowacourse.gongseek.auth.exception.NoSuchAuthenticationDataException;
import com.woowacourse.gongseek.comment.exception.CommentNotFoundException;
import com.woowacourse.gongseek.comment.exception.CommentNullOrEmptyException;
import com.woowacourse.gongseek.comment.exception.CommentTooLongException;
import com.woowacourse.gongseek.member.exception.MemberNotFoundException;
import com.woowacourse.gongseek.tag.exception.ExceededTagsException;
import com.woowacourse.gongseek.tag.exception.TagNameLengthException;
import com.woowacourse.gongseek.tag.exception.TagNameNullOrBlankException;
import com.woowacourse.gongseek.vote.exception.AlreadyVoteSameItemException;
import com.woowacourse.gongseek.vote.exception.InvalidVoteAmountException;
import com.woowacourse.gongseek.vote.exception.InvalidVoteExpiryAtException;
import com.woowacourse.gongseek.vote.exception.InvalidVoteItemCountException;
import com.woowacourse.gongseek.vote.exception.UnavailableArticleException;
import com.woowacourse.gongseek.vote.exception.VoteHistoryNotFoundException;
import com.woowacourse.gongseek.vote.exception.VoteItemNotFoundException;
import com.woowacourse.gongseek.vote.exception.VoteItemNullOrEmptyException;
import com.woowacourse.gongseek.vote.exception.VoteItemTooLongException;
import com.woowacourse.gongseek.vote.exception.VoteNotFoundException;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionType {

    NOT_FOUND_EXCEPTION_TYPE("0000", "해당 에러 타입을 찾을 수 없습니다.", UnSupportedExceptionType.class),

    GITHUB_ACCESS_TOKEN_LOAD_FAIL_EXCEPTION("1001", "Github API에서 Accesstoken을 받는것에 실패했습니다.",
            GithubAccessTokenLoadFailException.class),
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
    ARTICLE_TITLE_NULL_OR_EMPTY_EXCEPTION("3003", "게시글 제목은 비어있을 수 없습니다.", ArticleTitleNullOrEmptyException.class),
    ARTICLE_CONTENT_TOO_LONG_EXCEPTION("3004", "게시글 내용은 10000자를 초과할 수 없습니다.", ArticleContentTooLongException.class),
    ARTICLE_CONTENT_NULL_OR_EMPTY_EXCEPTION("3003", "게시글 내용은 비어있을 수 없습니다.", ArticleContentNullOrEmptyException.class),
    ARTICLE_CATEGORY_NOT_FOUND_EXCEPTION("3005", "게시글의 카테고리가 존재하지 않습니다.", ArticleCategoryNotFoundException.class),

    COMMENT_NOT_FOUND_EXCEPTION("4001", "댓글이 존재하지 않습니다.", CommentNotFoundException.class),
    COMMENT_NULL_OR_EMPTY_EXCEPTION("4002", "댓글은 비어있을 수 없습니다.", CommentNullOrEmptyException.class),
    COMMENT_TOO_LONG_EXCEPTION("4003", "댓글은 10000자를 초과할 수 없습니다.", CommentTooLongException.class),

    INVALID_VOTE_EXPIRY_DATE_EXCEPTION("5001", "투표 만료일 설정이 잘못되었습니다.", InvalidVoteExpiryAtException.class),
    VOTE_ITEM_NULL_OR_EMPTY_EXCEPTION("5002", "투표 항목 내용은 비어있을 수 없습니다.", VoteItemNullOrEmptyException.class),
    VOTE_ITEM_TOO_LONG_EXCEPTION("5003", "투표 항목 내용은 500자를 초과할 수 없습니다.", VoteItemTooLongException.class),
    VOTE_NOT_FOUND_EXCEPTION("5004", "투표가 존재하지 않습니다.", VoteNotFoundException.class),
    UNAVAILABLE_ARTICLE_EXCEPTION("5005", "토론 게시물만 투표를 생성할 수 있습니다.", UnavailableArticleException.class),
    INVALID_VOTE_AMOUNT_EXCEPTION("5006", "투표수는 양수여야만 합니다.", InvalidVoteAmountException.class),
    VOTE_ITEM_NOT_FOUND_EXCEPTION("5007", "투표 항목이 존재하지 않습니다.", VoteItemNotFoundException.class),
    VOTE_HISTORY_NOT_FOUND_EXCEPTION("5008", "투표 내역이 존재하지 않습니다.", VoteHistoryNotFoundException.class),
    ALREADY_VOTE_SAME_ITEM_EXCEPTION("5009", "이미 같은 항목에 투표했습니다.", AlreadyVoteSameItemException.class),
    INVALID_VOTE_ITEM_COUNT_EXCEPTION("5010", "투표 항목 수는 2이상 5이하여야 합니다.", InvalidVoteItemCountException.class),

    TAG_NAME_NULL_OR_BLANK_EXCEPTION("6001", "해시태그 이름은 비어있을 수 없습니다.", TagNameNullOrBlankException.class),
    TAG_NAME_LENGTH_EXCEPTION("6002", "해시태그 이름은 2자 이상 20자 이하입니다.", TagNameLengthException.class),
    DUPLICATE_TAG_EXCEPTION("6003", "해시태그 이름은 중복될 수 없습니다.", DuplicateTagException.class),
    EXCEEDED_TAGS_EXCEPTION("6004", "해시태그는 한 게시글 당 최대 5개입니다.", ExceededTagsException.class),
    ;

    private final String errorCode;
    private final String message;
    private final Class<? extends ApplicationException> type;

    public static ExceptionType from(Class<?> classType) {
        return Arrays.stream(values())
                .filter(it -> it.type.equals(classType))
                .findFirst()
                .orElseThrow(UnSupportedExceptionType::new);
    }
}
