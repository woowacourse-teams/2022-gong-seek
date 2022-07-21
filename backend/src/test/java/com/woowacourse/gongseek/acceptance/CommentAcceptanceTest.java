package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.게시물을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.AuthFixtures.로그인을_한다;
import static com.woowacourse.gongseek.acceptance.support.CommentFixtures.댓글을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.CommentFixtures.댓글을_삭제한다;
import static com.woowacourse.gongseek.acceptance.support.CommentFixtures.댓글을_수정한다;
import static com.woowacourse.gongseek.acceptance.support.CommentFixtures.댓글을_조회한다;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.주디;
import static org.assertj.core.api.Assertions.assertThat;

import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.comment.presentation.dto.CommentResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class CommentAcceptanceTest extends AcceptanceTest {

    @Test
    void 유저가_깃허브로_로그인을_하고_댓글을_등록할_수_있다() {
        TokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시글번호 = 게시물을_등록한다(엑세스토큰);

        ExtractableResponse<Response> 댓글 = 댓글을_등록한다(엑세스토큰, 게시글번호);

        assertThat(댓글.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }


    @Test
    void 유저가_로그인을_하지_않고_댓글을_등록할_수_없다() {
        ExtractableResponse<Response> 댓글 = 댓글을_등록한다(new TokenResponse(""), new ArticleIdResponse(1L));

        assertThat(댓글.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 댓글을_조회할_수_있다() {
        TokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시글번호 = 게시물을_등록한다(엑세스토큰);

        댓글을_등록한다(엑세스토큰, 게시글번호);

        List<CommentResponse> 댓글리스트 = 댓글을_조회한다(엑세스토큰, 게시글번호).getComments();

        assertThat(댓글리스트.size()).isEqualTo(1);
    }

    @Test
    void 댓글을_작성한_유저일_경우_댓글을_수정할_수_있다() {
        TokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시글번호 = 게시물을_등록한다(엑세스토큰);
        댓글을_등록한다(엑세스토큰, 게시글번호);
        List<CommentResponse> 댓글리스트 = 댓글을_조회한다(엑세스토큰, 게시글번호).getComments();

        ExtractableResponse<Response> 수정된댓글 = 댓글을_수정한다(엑세스토큰, 댓글리스트);

        assertThat(수정된댓글.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void 댓글을_작성한_유저일_경우_댓글을_삭제할_수_있다() {
        TokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시글번호 = 게시물을_등록한다(엑세스토큰);
        댓글을_등록한다(엑세스토큰, 게시글번호);
        List<CommentResponse> 댓글리스트 = 댓글을_조회한다(엑세스토큰, 게시글번호).getComments();

        ExtractableResponse<Response> 삭제된댓글 = 댓글을_삭제한다(엑세스토큰, 댓글리스트);

        assertThat(삭제된댓글.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
