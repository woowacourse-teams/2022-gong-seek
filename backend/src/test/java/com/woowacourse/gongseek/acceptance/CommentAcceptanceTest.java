package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.토론_게시물을_기명으로_등록한다;
import static com.woowacourse.gongseek.acceptance.support.AuthFixtures.로그인을_한다;
import static com.woowacourse.gongseek.acceptance.support.CommentFixtures.기명으로_댓글을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.CommentFixtures.댓글을_삭제한다;
import static com.woowacourse.gongseek.acceptance.support.CommentFixtures.댓글을_수정한다;
import static com.woowacourse.gongseek.acceptance.support.CommentFixtures.댓글을_조회한다;
import static com.woowacourse.gongseek.acceptance.support.CommentFixtures.익명으로_댓글을_등록한다;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.슬로;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.주디;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.auth.presentation.dto.AccessTokenResponse;
import com.woowacourse.gongseek.comment.presentation.dto.CommentResponse;
import com.woowacourse.gongseek.common.exception.dto.ErrorResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class CommentAcceptanceTest extends AcceptanceTest {

    @Test
    void 유저가_깃허브로_로그인을_하고_기명_댓글을_등록할_수_있다() {
        //given
        AccessTokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시글번호 = 토론_게시물을_기명으로_등록한다(엑세스토큰);

        //when
        ExtractableResponse<Response> 댓글 = 기명으로_댓글을_등록한다(엑세스토큰, 게시글번호);

        //then
        assertThat(댓글.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 유저가_깃허브로_로그인을_하고_익명_댓글을_등록할_수_있다() {
        //given
        AccessTokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시글번호 = 토론_게시물을_기명으로_등록한다(엑세스토큰);

        //when
        ExtractableResponse<Response> 댓글 = 익명으로_댓글을_등록한다(엑세스토큰, 게시글번호);

        //then
        assertThat(댓글.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 유저가_로그인을_하지_않고_기명_댓글을_등록할_수_없다() {
        //given
        AccessTokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시글번호 = 토론_게시물을_기명으로_등록한다(엑세스토큰);
        AccessTokenResponse 비회원 = new AccessTokenResponse(null);

        //when
        ExtractableResponse<Response> 댓글 = 기명으로_댓글을_등록한다(비회원, new ArticleIdResponse(게시글번호.getId()));
        ErrorResponse response = 기명으로_댓글을_등록한다(비회원, new ArticleIdResponse(게시글번호.getId())).as(ErrorResponse.class);

        //then
        assertAll(
                () -> assertThat(response.getErrorCode()).isEqualTo("1008"),
                () -> assertThat(response.getMessage()).isEqualTo("회원이 아니므로 권한이 없습니다.")
        );
    }

    @Test
    void 유저가_로그인을_하지_않고_익명_댓글을_등록할_수_없다() {
        //given
        AccessTokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시글번호 = 토론_게시물을_기명으로_등록한다(엑세스토큰);
        AccessTokenResponse 비회원 = new AccessTokenResponse(null);

        //when
        ErrorResponse response = 익명으로_댓글을_등록한다(비회원, new ArticleIdResponse(게시글번호.getId())).as(ErrorResponse.class);

        //then
        assertAll(
                () -> assertThat(response.getErrorCode()).isEqualTo("1008"),
                () -> assertThat(response.getMessage()).isEqualTo("회원이 아니므로 권한이 없습니다.")
        );
    }

    @Test
    void 기명_댓글을_조회할_수_있다() {
        //given
        AccessTokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시글번호 = 토론_게시물을_기명으로_등록한다(엑세스토큰);
        기명으로_댓글을_등록한다(엑세스토큰, 게시글번호);

        //when
        List<CommentResponse> 댓글리스트 = 댓글을_조회한다(엑세스토큰, 게시글번호).getComments();

        //then
        assertThat(댓글리스트).hasSize(1);
    }

    @Test
    void 익명_댓글을_조회할_수_있다() {
        //given
        AccessTokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시글번호 = 토론_게시물을_기명으로_등록한다(엑세스토큰);
        익명으로_댓글을_등록한다(엑세스토큰, 게시글번호);

        //when
        List<CommentResponse> 댓글리스트 = 댓글을_조회한다(엑세스토큰, 게시글번호).getComments();

        //then
        assertThat(댓글리스트).hasSize(1);
    }

    @Test
    void 기명_댓글을_작성한_유저일_경우_수정할_수_있다() {
        //given
        AccessTokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시글번호 = 토론_게시물을_기명으로_등록한다(엑세스토큰);
        기명으로_댓글을_등록한다(엑세스토큰, 게시글번호);
        List<CommentResponse> 댓글리스트 = 댓글을_조회한다(엑세스토큰, 게시글번호).getComments();

        //when
        ExtractableResponse<Response> 수정된댓글 = 댓글을_수정한다(엑세스토큰, 댓글리스트);

        //then
        assertThat(수정된댓글.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 익명_댓글을_작성한_유저일_경우_수정할_수_있다() {
        //given
        AccessTokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시글번호 = 토론_게시물을_기명으로_등록한다(엑세스토큰);
        익명으로_댓글을_등록한다(엑세스토큰, 게시글번호);
        List<CommentResponse> 댓글리스트 = 댓글을_조회한다(엑세스토큰, 게시글번호).getComments();

        //when
        ExtractableResponse<Response> 수정된댓글 = 댓글을_수정한다(엑세스토큰, 댓글리스트);

        //then
        assertThat(수정된댓글.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 기명_댓글의_작성자가_아닌_경우_수정할_수_없다() {
        //given
        AccessTokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시글번호 = 토론_게시물을_기명으로_등록한다(엑세스토큰);
        기명으로_댓글을_등록한다(엑세스토큰, 게시글번호);

        AccessTokenResponse 비작성자 = 로그인을_한다(슬로);
        List<CommentResponse> 댓글리스트 = 댓글을_조회한다(비작성자, 게시글번호).getComments();

        //when
        ExtractableResponse<Response> response = 댓글을_수정한다(비작성자, 댓글리스트);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value()),
                () -> assertThat(errorResponse.getErrorCode()).isEqualTo("1007"),
                () -> assertThat(errorResponse.getMessage()).contains("작성자가 아니므로 권한이 없습니다.")
        );
    }

    @Test
    void 익명_댓글의_작성자가_아닌_경우_수정할_수_없다() {
        //given
        AccessTokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시글번호 = 토론_게시물을_기명으로_등록한다(엑세스토큰);
        익명으로_댓글을_등록한다(엑세스토큰, 게시글번호);

        AccessTokenResponse 비작성자 = 로그인을_한다(슬로);
        List<CommentResponse> 댓글리스트 = 댓글을_조회한다(비작성자, 게시글번호).getComments();

        //when
        ExtractableResponse<Response> response = 댓글을_수정한다(비작성자, 댓글리스트);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value()),
                () -> assertThat(errorResponse.getErrorCode()).isEqualTo("1007"),
                () -> assertThat(errorResponse.getMessage()).contains("작성자가 아니므로 권한이 없습니다.")
        );
    }

    @Test
    void 기명_댓글을_작성한_유저일_경우_삭제할_수_있다() {
        //given
        AccessTokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시글번호 = 토론_게시물을_기명으로_등록한다(엑세스토큰);
        기명으로_댓글을_등록한다(엑세스토큰, 게시글번호);
        List<CommentResponse> 댓글리스트 = 댓글을_조회한다(엑세스토큰, 게시글번호).getComments();

        //when
        ExtractableResponse<Response> 삭제된댓글 = 댓글을_삭제한다(엑세스토큰, 댓글리스트);

        //then
        assertThat(삭제된댓글.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 익명_댓글을_작성한_유저일_경우_삭제할_수_있다() {
        AccessTokenResponse 엑세스토큰 = 로그인을_한다(주디);
        //given
        ArticleIdResponse 게시글번호 = 토론_게시물을_기명으로_등록한다(엑세스토큰);
        익명으로_댓글을_등록한다(엑세스토큰, 게시글번호);
        List<CommentResponse> 댓글리스트 = 댓글을_조회한다(엑세스토큰, 게시글번호).getComments();

        //when
        ExtractableResponse<Response> 삭제된댓글 = 댓글을_삭제한다(엑세스토큰, 댓글리스트);

        //then
        assertThat(삭제된댓글.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 기명_댓글의_작성자가_아닌_경우_삭제할_수_없다() {
        //given
        AccessTokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시글번호 = 토론_게시물을_기명으로_등록한다(엑세스토큰);
        기명으로_댓글을_등록한다(엑세스토큰, 게시글번호);

        AccessTokenResponse 비작성자 = 로그인을_한다(슬로);
        List<CommentResponse> 댓글리스트 = 댓글을_조회한다(비작성자, 게시글번호).getComments();

        //when
        ExtractableResponse<Response> response = 댓글을_삭제한다(비작성자, 댓글리스트);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value()),
                () -> assertThat(errorResponse.getErrorCode()).isEqualTo("1007"),
                () -> assertThat(errorResponse.getMessage()).contains("작성자가 아니므로 권한이 없습니다.")
        );
    }

    @Test
    void 익명_댓글의_작성자가_아닌_경우_삭제할_수_없다() {
        //given
        AccessTokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시글번호 = 토론_게시물을_기명으로_등록한다(엑세스토큰);
        익명으로_댓글을_등록한다(엑세스토큰, 게시글번호);

        AccessTokenResponse 비작성자 = 로그인을_한다(슬로);
        List<CommentResponse> 댓글리스트 = 댓글을_조회한다(비작성자, 게시글번호).getComments();

        //when
        ExtractableResponse<Response> response = 댓글을_삭제한다(비작성자, 댓글리스트);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value()),
                () -> assertThat(errorResponse.getErrorCode()).isEqualTo("1007"),
                () -> assertThat(errorResponse.getMessage()).contains("작성자가 아니므로 권한이 없습니다.")
        );
    }
}
