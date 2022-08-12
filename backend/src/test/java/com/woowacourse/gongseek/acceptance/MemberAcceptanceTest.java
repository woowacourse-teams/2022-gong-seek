package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.기명으로_게시물을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.익명으로_게시물을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.토론_게시물을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.AuthFixtures.로그인을_한다;
import static com.woowacourse.gongseek.acceptance.support.CommentFixtures.기명으로_댓글을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.CommentFixtures.익명으로_댓글을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.MemberFixtures.내_정보를_조회한다;
import static com.woowacourse.gongseek.acceptance.support.MemberFixtures.내가_작성한_게시글들을_조회한다;
import static com.woowacourse.gongseek.acceptance.support.MemberFixtures.내가_작성한_댓글들을_조회한다;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.레넌;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.auth.presentation.dto.AccessTokenResponse;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
import com.woowacourse.gongseek.member.presentation.dto.MyPageArticlesResponse;
import com.woowacourse.gongseek.member.presentation.dto.MyPageCommentsResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class MemberAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원_정보를_조회한다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(레넌);

        // when
        ExtractableResponse<Response> response = 내_정보를_조회한다(tokenResponse);
        AuthorDto authorDto = response.as(AuthorDto.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(authorDto.getName()).isEqualTo(레넌.getName()),
                () -> assertThat(authorDto.getAvatarUrl()).isEqualTo(레넌.getAvatarUrl())
        );
    }

    @Test
    void 회원이_작성한_기명_게시글들을_조회한다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(레넌);
        토론_게시물을_등록한다(tokenResponse);

        // when
        ExtractableResponse<Response> response = 내가_작성한_게시글들을_조회한다(tokenResponse);
        MyPageArticlesResponse myPageArticlesResponse = response.as(MyPageArticlesResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(myPageArticlesResponse.getArticles()).hasSize(1)
        );
    }

    @Test
    void 회원이_작성한_익명_기명_게시글을_모두_조회한다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(레넌);
        기명으로_게시물을_등록한다(tokenResponse, Category.QUESTION);
        익명으로_게시물을_등록한다(tokenResponse, Category.DISCUSSION);

        // when
        ExtractableResponse<Response> response = 내가_작성한_게시글들을_조회한다(tokenResponse);
        MyPageArticlesResponse myPageArticlesResponse = response.as(MyPageArticlesResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(myPageArticlesResponse.getArticles()).hasSize(2)
        );
    }

    @Test
    void 회원이_작성한_익명_기명_댓글들을_조회한다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(레넌);
        ArticleIdResponse 게시글번호 = 토론_게시물을_등록한다(tokenResponse);
        기명으로_댓글을_등록한다(tokenResponse, 게시글번호);
        익명으로_댓글을_등록한다(tokenResponse, 게시글번호);

        // when
        ExtractableResponse<Response> response = 내가_작성한_댓글들을_조회한다(tokenResponse);
        MyPageCommentsResponse myPageCommentsResponse = response.as(MyPageCommentsResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(myPageCommentsResponse.getComments()).hasSize(2)
        );
    }
}
