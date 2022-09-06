package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.게시글_전체를_조회한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.게시글_전체를_추천순으로_조회한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.게시글을_유저이름으로_검색한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.게시글을_제목과_내용으로_검색한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.게시글을_제목과_내용으로_처음_검색한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.기명으로_게시글을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.로그인_후_게시글을_삭제한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.로그인_후_게시글을_수정한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.로그인_후_게시글을_조회한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.로그인_후_해시태그로_게시글들을_검색한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.로그인을_하지_않고_게시글을_수정한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.로그인을_하지_않고_게시글을_조회한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.익명으로_게시글을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.조회수가_있는_게시글_5개를_생성한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.토론_게시글을_기명으로_등록한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.특정_게시글을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.해시태그_없이_게시글을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.해시태그로_게시글들을_검색한다;
import static com.woowacourse.gongseek.acceptance.support.AuthFixtures.로그인을_한다;
import static com.woowacourse.gongseek.acceptance.support.CommentFixtures.기명으로_댓글을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.LikeFixtures.게시글을_추천한다;
import static com.woowacourse.gongseek.acceptance.support.VoteFixtures.투표를_생성한다;
import static com.woowacourse.gongseek.acceptance.support.VoteFixtures.투표를_한다;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.기론;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.레넌;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.슬로;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.주디;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticlePageResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticlePreviewResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateResponse;
import com.woowacourse.gongseek.auth.presentation.dto.AccessTokenResponse;
import com.woowacourse.gongseek.common.exception.dto.ErrorResponse;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
import com.woowacourse.gongseek.vote.presentation.dto.SelectVoteItemIdRequest;
import com.woowacourse.gongseek.vote.presentation.dto.VoteCreateRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class ArticleAcceptanceTest extends AcceptanceTest {

    private static final AuthorDto anonymousAuthor = new AuthorDto(
            "익명",
            "https://raw.githubusercontent.com/woowacourse-teams/2022-gong-seek/develop/frontend/src/assets/gongseek.png"
    );

    @Test
    void 유저가_깃허브로_로그인을_하고_기명으로_게시글을_등록할_수_있다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);

        // when
        ExtractableResponse<Response> response = 기명으로_게시글을_등록한다(tokenResponse, Category.QUESTION);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 유저가_깃허브로_로그인을_하고_익명으로_게시글을_등록할_수_있다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);

        // when
        ExtractableResponse<Response> response = 익명으로_게시글을_등록한다(tokenResponse, Category.QUESTION);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 유저가_깃허브로_로그인을_하고_해시태그를_포함하지_않고_게시글을_등록할_수_있다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(레넌);

        // when
        ExtractableResponse<Response> response = 해시태그_없이_게시글을_등록한다(tokenResponse, Category.QUESTION);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void 유저가_깃허브로_로그인을_하지_않고_기명으로_게시글을_등록할_수_없다() {
        //given
        //when
        ErrorResponse errorResponse = 기명으로_게시글을_등록한다(new AccessTokenResponse(null), Category.QUESTION).as(
                ErrorResponse.class);

        //then
        assertAll(
                () -> assertThat(errorResponse.getErrorCode()).isEqualTo("1008"),
                () -> assertThat(errorResponse.getMessage()).isEqualTo("회원이 아니므로 권한이 없습니다.")
        );
    }

    @Test
    void 유저가_깃허브로_로그인을_하지_않고_익명으로_게시글을_등록할_수_없다() {
        //given
        // when
        ErrorResponse response = 익명으로_게시글을_등록한다(new AccessTokenResponse(null), Category.QUESTION).as(
                ErrorResponse.class);

        //then
        assertAll(
                () -> assertThat(response.getErrorCode()).isEqualTo("1008"),
                () -> assertThat(response.getMessage()).isEqualTo("회원이 아니므로 권한이 없습니다.")
        );
    }

    @Test
    void 로그인_없이_기명_게시글을_단건_조회할_수_있다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 기명으로_게시글을_등록한다(tokenResponse, Category.QUESTION).as(
                ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인을_하지_않고_게시글을_조회한다(articleIdResponse);
        ArticleResponse articleResponse = response.as(ArticleResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articleResponse)
                        .usingRecursiveComparison()
                        .ignoringFields("createdAt")
                        .ignoringFields("updatedAt")
                        .isEqualTo(
                                new ArticleResponse(
                                        "title",
                                        List.of("SPRING"),
                                        new AuthorDto("주디", "https://avatars.githubusercontent.com/u/78091011?v=4"),
                                        "content",
                                        false,
                                        1,
                                        false,
                                        false,
                                        0L,
                                        LocalDateTime.now(),
                                        LocalDateTime.now()
                                )
                        )
        );
    }

    @Test
    void 로그인_없이_익명_게시글을_단건_조회할_수_있다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 익명으로_게시글을_등록한다(tokenResponse, Category.QUESTION).as(
                ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인을_하지_않고_게시글을_조회한다(articleIdResponse);
        ArticleResponse articleResponse = response.as(ArticleResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articleResponse)
                        .usingRecursiveComparison()
                        .ignoringFields("createdAt")
                        .ignoringFields("updatedAt")
                        .isEqualTo(
                                new ArticleResponse(
                                        "title",
                                        List.of("SPRING"),
                                        anonymousAuthor,
                                        "content",
                                        false,
                                        1,
                                        false,
                                        false,
                                        0L,
                                        LocalDateTime.now(),
                                        LocalDateTime.now()
                                )
                        )
        );
    }

    @Test
    void 작성자가_로그인을_하고_기명_게시글을_단건_조회할_수_있다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 기명으로_게시글을_등록한다(tokenResponse, Category.QUESTION).as(
                ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_후_게시글을_조회한다(tokenResponse, articleIdResponse);
        ArticleResponse articleResponse = response.as(ArticleResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articleResponse)
                        .usingRecursiveComparison()
                        .ignoringFields("createdAt")
                        .ignoringFields("updatedAt")
                        .isEqualTo(
                                new ArticleResponse(
                                        "title",
                                        List.of("SPRING"),
                                        new AuthorDto("주디", "https://avatars.githubusercontent.com/u/78091011?v=4"),
                                        "content",
                                        true,
                                        1,
                                        false,
                                        false,
                                        0L,
                                        LocalDateTime.now(),
                                        LocalDateTime.now()
                                )
                        )
        );
    }

    @Test
    void 작성자가_로그인을_하고_익명_게시글을_단건_조회할_수_있다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 익명으로_게시글을_등록한다(tokenResponse, Category.QUESTION).as(
                ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_후_게시글을_조회한다(tokenResponse, articleIdResponse);
        ArticleResponse articleResponse = response.as(ArticleResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articleResponse)
                        .usingRecursiveComparison()
                        .ignoringFields("createdAt")
                        .ignoringFields("updatedAt")
                        .isEqualTo(
                                new ArticleResponse(
                                        "title",
                                        List.of("SPRING"),
                                        anonymousAuthor,
                                        "content",
                                        true,
                                        1,
                                        false,
                                        false,
                                        0L,
                                        LocalDateTime.now(),
                                        LocalDateTime.now()
                                )
                        )
        );
    }

    @Test
    void 게시글을_단건_조회를_계속_하면_조회수가_계속_증가한다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 기명으로_게시글을_등록한다(tokenResponse, Category.QUESTION).as(
                ArticleIdResponse.class);

        // when
        로그인_후_게시글을_조회한다(tokenResponse, articleIdResponse);
        ExtractableResponse<Response> response = 로그인_후_게시글을_조회한다(tokenResponse, articleIdResponse);
        ArticleResponse articleResponse = response.as(ArticleResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articleResponse)
                        .usingRecursiveComparison()
                        .ignoringFields("createdAt")
                        .ignoringFields("updatedAt")
                        .isEqualTo(
                                new ArticleResponse(
                                        "title",
                                        List.of("SPRING"),
                                        new AuthorDto("주디", "https://avatars.githubusercontent.com/u/78091011?v=4"),
                                        "content",
                                        true,
                                        2,
                                        false,
                                        false,
                                        0L,
                                        LocalDateTime.now(),
                                        LocalDateTime.now()
                                )
                        )
        );
    }

    @Test
    void 로그인_하지_않으면_기명_게시글을_수정할_수_없다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 기명으로_게시글을_등록한다(tokenResponse, Category.QUESTION).as(
                ArticleIdResponse.class);

        //when
        ErrorResponse response = 로그인을_하지_않고_게시글을_수정한다(articleIdResponse).as(ErrorResponse.class);

        //then
        assertAll(
                () -> assertThat(response.getErrorCode()).isEqualTo("1008"),
                () -> assertThat(response.getMessage()).isEqualTo("회원이 아니므로 권한이 없습니다.")
        );
    }

    @Test
    void 로그인_하지_않으면_익명_게시글을_수정할_수_없다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 익명으로_게시글을_등록한다(tokenResponse, Category.QUESTION).as(
                ArticleIdResponse.class);

        //when
        ErrorResponse response = 로그인을_하지_않고_게시글을_수정한다(articleIdResponse).as(ErrorResponse.class);

        //then
        assertAll(
                () -> assertThat(response.getErrorCode()).isEqualTo("1008"),
                () -> assertThat(response.getMessage()).isEqualTo("회원이 아니므로 권한이 없습니다.")
        );
    }

    @Test
    void 게시글_작성자가_아니면_기명_게시글을_수정할_수_없다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 기명으로_게시글을_등록한다(tokenResponse, Category.QUESTION).as(
                ArticleIdResponse.class);

        //when
        AccessTokenResponse nonAuthorToken = 로그인을_한다(슬로);
        ErrorResponse response = 로그인_후_게시글을_수정한다(nonAuthorToken, articleIdResponse).as(ErrorResponse.class);

        //then
        assertAll(
                () -> assertThat(response.getErrorCode()).isEqualTo("1007"),
                () -> assertThat(response.getMessage()).contains("작성자가 아니므로 권한이 없습니다.")
        );
    }

    @Test
    void 게시글_작성자가_아니면_익명_게시글을_수정할_수_없다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 익명으로_게시글을_등록한다(tokenResponse, Category.QUESTION).as(
                ArticleIdResponse.class);

        // when
        AccessTokenResponse nonAuthorToken = 로그인을_한다(슬로);
        ErrorResponse response = 로그인_후_게시글을_수정한다(nonAuthorToken, articleIdResponse).as(ErrorResponse.class);

        //then
        assertAll(
                () -> assertThat(response.getErrorCode()).isEqualTo("1007"),
                () -> assertThat(response.getMessage()).contains("작성자가 아니므로 권한이 없습니다.")
        );
    }

    @Test
    void 게시글_작성자는_기명_게시글을_수정할_수_있다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 기명으로_게시글을_등록한다(tokenResponse, Category.QUESTION).as(
                ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_후_게시글을_수정한다(tokenResponse, articleIdResponse);
        ArticleUpdateResponse articleUpdateResponse = response.as(ArticleUpdateResponse.class);

        ArticleResponse articleResponse = 로그인을_하지_않고_게시글을_조회한다(articleIdResponse).as(ArticleResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articleUpdateResponse.getId()).isEqualTo(articleIdResponse.getId()),
                () -> assertThat(articleUpdateResponse.getCategory()).isEqualTo(Category.QUESTION.getValue()),
                () -> assertThat(articleResponse.getTag()).hasSize(1),
                () -> assertThat(articleResponse.getTag().get(0)).isEqualTo("JAVA")
        );
    }

    @Test
    void 게시글_작성자는_익명_게시글을_수정할_수_있다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 익명으로_게시글을_등록한다(tokenResponse, Category.QUESTION).as(
                ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_후_게시글을_수정한다(tokenResponse, articleIdResponse);
        ArticleUpdateResponse articleUpdateResponse = response.as(ArticleUpdateResponse.class);

        ArticleResponse articleResponse = 로그인을_하지_않고_게시글을_조회한다(articleIdResponse).as(ArticleResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articleUpdateResponse.getId()).isEqualTo(articleIdResponse.getId()),
                () -> assertThat(articleUpdateResponse.getCategory()).isEqualTo(Category.QUESTION.getValue()),
                () -> assertThat(articleResponse.getTag()).hasSize(1),
                () -> assertThat(articleResponse.getTag().get(0)).isEqualTo("JAVA")
        );
    }

    @Test
    void 게시글_작성자는_기명_게시글을_삭제할_수_있다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 기명으로_게시글을_등록한다(tokenResponse, Category.QUESTION).as(
                ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_후_게시글을_삭제한다(tokenResponse, articleIdResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 게시글_작성자는_익명_게시글을_삭제할_수_있다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 익명으로_게시글을_등록한다(tokenResponse, Category.QUESTION).as(
                ArticleIdResponse.class);

        // when
        ExtractableResponse<Response> response = 로그인_후_게시글을_삭제한다(tokenResponse, articleIdResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 게시글_작성자가_아니면_기명_게시글을_삭제할_수_없다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 기명으로_게시글을_등록한다(tokenResponse, Category.QUESTION).as(
                ArticleIdResponse.class);
        AccessTokenResponse nonAuthorToken = 로그인을_한다(슬로);

        //when
        ErrorResponse response = 로그인_후_게시글을_삭제한다(nonAuthorToken, articleIdResponse).as(ErrorResponse.class);

        //then
        assertAll(
                () -> assertThat(response.getErrorCode()).isEqualTo("1007"),
                () -> assertThat(response.getMessage()).contains("작성자가 아니므로 권한이 없습니다.")
        );
    }

    @Test
    void 게시글_작성자가_아니면_익명_게시글을_삭제할_수_없다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        ArticleIdResponse articleIdResponse = 익명으로_게시글을_등록한다(tokenResponse, Category.QUESTION).as(
                ArticleIdResponse.class);
        AccessTokenResponse nonAuthorToken = 로그인을_한다(슬로);

        //when
        ErrorResponse response = 로그인_후_게시글을_삭제한다(nonAuthorToken, articleIdResponse).as(ErrorResponse.class);

        //then
        assertAll(
                () -> assertThat(response.getErrorCode()).isEqualTo("1007"),
                () -> assertThat(response.getMessage()).contains("작성자가 아니므로 권한이 없습니다.")
        );
    }

    @Test
    void 전체_게시글을_최신순으로_조회한다() {
        //given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        조회수가_있는_게시글_5개를_생성한다(tokenResponse, 0, Category.DISCUSSION);
        조회수가_있는_게시글_5개를_생성한다(tokenResponse, 2, Category.DISCUSSION);
        조회수가_있는_게시글_5개를_생성한다(tokenResponse, 3, Category.QUESTION);
        조회수가_있는_게시글_5개를_생성한다(tokenResponse, 1, Category.QUESTION);

        //when
        ExtractableResponse<Response> response = 게시글_전체를_조회한다("all", "latest", null, null);
        ArticlePageResponse firstResponse = response.as(ArticlePageResponse.class);
        List<ArticlePreviewResponse> firstArticles = firstResponse.getArticles();

        ExtractableResponse<Response> secondResponse = 게시글_전체를_조회한다("all", "latest",
                firstArticles.get(firstArticles.size() - 1).getId(), null);
        ArticlePageResponse secondArticles = secondResponse.as(ArticlePageResponse.class);

        //then
        assertAll(
                () -> assertThat(secondResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(secondArticles.hasNext()).isFalse(),
                () -> assertThat(secondArticles.getArticles().get(9).getId()).isEqualTo(1L),
                () -> assertThat(secondArticles.getArticles().get(0))
                        .usingRecursiveComparison()
                        .ignoringFields("createdAt")
                        .isEqualTo(
                                new ArticlePreviewResponse(
                                        10L,
                                        "title",
                                        List.of("SPRING"),
                                        new AuthorDto("주디", "https://avatars.githubusercontent.com/u/78091011?v=4"),
                                        "content",
                                        "discussion",
                                        0,
                                        2,
                                        false,
                                        0L,
                                        LocalDateTime.now()
                                )
                        )
        );
    }

    @Test
    void 전체_게시글을_조회순으로_조회한다() {
        //given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        조회수가_있는_게시글_5개를_생성한다(tokenResponse, 5, Category.DISCUSSION);
        조회수가_있는_게시글_5개를_생성한다(tokenResponse, 0, Category.QUESTION);
        조회수가_있는_게시글_5개를_생성한다(tokenResponse, 3, Category.DISCUSSION);
        조회수가_있는_게시글_5개를_생성한다(tokenResponse, 1, Category.QUESTION);

        //when
        ExtractableResponse<Response> response = 게시글_전체를_조회한다("all", "views", null, null);
        ArticlePageResponse firstResponse = response.as(ArticlePageResponse.class);
        List<ArticlePreviewResponse> firstArticles = firstResponse.getArticles();

        ArticlePreviewResponse lastArticle = firstArticles.get(firstArticles.size() - 1);
        ExtractableResponse<Response> secondResponse = 게시글_전체를_조회한다("all", "views",
                lastArticle.getId(), lastArticle.getViews());
        ArticlePageResponse secondArticles = secondResponse.as(ArticlePageResponse.class);

        //then
        assertAll(
                () -> assertThat(secondResponse.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(secondArticles.hasNext()).isFalse(),
                () -> assertThat(secondArticles.getArticles().get(9).getViews()).isEqualTo(0),
                () -> assertThat(secondArticles.getArticles().get(0))
                        .usingRecursiveComparison()
                        .ignoringFields("createdAt", "id")
                        .isEqualTo(
                                new ArticlePreviewResponse(
                                        16L,
                                        "title",
                                        List.of("SPRING"),
                                        new AuthorDto("주디", "https://avatars.githubusercontent.com/u/78091011?v=4"),
                                        "content",
                                        "question",
                                        0,
                                        1,
                                        false,
                                        0L,
                                        LocalDateTime.now()
                                )
                        )
        );
    }

    @Test
    void 전체_게시글을_조회하면_댓글_개수도_조회한다() {
        //given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        for (int i = 0; i < 5; i++) {
            ArticleIdResponse articleIdResponse = 기명으로_게시글을_등록한다(tokenResponse, Category.DISCUSSION).as(
                    ArticleIdResponse.class);
            로그인을_하지_않고_게시글을_조회한다(articleIdResponse);
            기명으로_댓글을_등록한다(tokenResponse, articleIdResponse);
        }
        for (int i = 0; i < 10; i++) {
            기명으로_게시글을_등록한다(tokenResponse, Category.QUESTION).as(ArticleIdResponse.class);
        }

        //when
        ExtractableResponse<Response> response = 게시글_전체를_조회한다("all", "views", null, null);
        ArticlePageResponse articlePageResponse = response.as(ArticlePageResponse.class);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlePageResponse.hasNext()).isTrue(),
                () -> assertThat(articlePageResponse.getArticles().get(8).getCommentCount()).isEqualTo(0),
                () -> assertThat(articlePageResponse.getArticles().get(0))
                        .usingRecursiveComparison()
                        .ignoringFields("createdAt", "id")
                        .isEqualTo(
                                new ArticlePreviewResponse(
                                        1L,
                                        "title",
                                        List.of("SPRING"),
                                        new AuthorDto("주디", "https://avatars.githubusercontent.com/u/78091011?v=4"),
                                        "content",
                                        "discussion",
                                        1,
                                        1,
                                        false,
                                        0L,
                                        LocalDateTime.now()
                                )
                        )
        );
    }

    @Test
    void 질문_게시글을_최신순으로_조회한다() {
        //given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        for (int i = 0; i < 20; i++) {
            기명으로_게시글을_등록한다(tokenResponse, Category.QUESTION).as(ArticleIdResponse.class);
        }

        //when
        ExtractableResponse<Response> response = 게시글_전체를_조회한다(Category.QUESTION.getValue(), "latest", null, null);
        ArticlePageResponse articlePageResponse = response.as(ArticlePageResponse.class);

        List<Long> ids = articlePageResponse.getArticles().stream()
                .map(ArticlePreviewResponse::getId)
                .collect(Collectors.toList());
        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlePageResponse.hasNext()).isTrue(),
                () -> assertThat(ids).isEqualTo(List.of(20L, 19L, 18L, 17L, 16L, 15L, 14L, 13L, 12L, 11L))
        );
    }

    @Test
    void 질문_게시글을_조회순으로_조회한다() {
        //given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        조회수가_있는_게시글_5개를_생성한다(tokenResponse, 1, Category.QUESTION);
        조회수가_있는_게시글_5개를_생성한다(tokenResponse, 2, Category.QUESTION);
        조회수가_있는_게시글_5개를_생성한다(tokenResponse, 0, Category.QUESTION);

        //when
        ExtractableResponse<Response> response = 게시글_전체를_조회한다(Category.QUESTION.getValue(), "views", null, null);
        ArticlePageResponse articlePageResponse = response.as(ArticlePageResponse.class);

        List<Long> ids = articlePageResponse.getArticles().stream()
                .map(ArticlePreviewResponse::getId)
                .limit(5)
                .collect(Collectors.toList());

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlePageResponse.hasNext()).isTrue(),
                () -> assertThat(ids).containsAll(List.of(6L, 7L, 8L, 9L, 10L))
        );
    }

    @Test
    void 토론_게시글을_최신순으로_조회한다() {
        //given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        조회수가_있는_게시글_5개를_생성한다(tokenResponse, 0, Category.QUESTION);
        조회수가_있는_게시글_5개를_생성한다(tokenResponse, 0, Category.QUESTION);

        //when
        ExtractableResponse<Response> response = 게시글_전체를_조회한다(Category.QUESTION.getValue(), "views", null, null);
        ArticlePageResponse articlePageResponse = response.as(ArticlePageResponse.class);

        List<Long> ids = articlePageResponse.getArticles().stream()
                .map(ArticlePreviewResponse::getId)
                .collect(Collectors.toList());

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlePageResponse.hasNext()).isFalse(),
                () -> assertThat(ids).containsAll(List.of(10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L, 1L))
        );
    }

    @Test
    void 토론_게시글을_조회순으로_조회한다() {
        //given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        조회수가_있는_게시글_5개를_생성한다(tokenResponse, 3, Category.DISCUSSION);
        조회수가_있는_게시글_5개를_생성한다(tokenResponse, 0, Category.DISCUSSION);

        //when
        ExtractableResponse<Response> response = 게시글_전체를_조회한다(Category.DISCUSSION.getValue(), "views",
                null, null);
        ArticlePageResponse articlePageResponse = response.as(ArticlePageResponse.class);

        List<Long> ids = articlePageResponse.getArticles().stream()
                .map(ArticlePreviewResponse::getId)
                .collect(Collectors.toList());

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlePageResponse.hasNext()).isFalse(),
                () -> assertThat(ids).containsAll(List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L))
        );
    }

    @Test
    void 특정_검색어로_게시글을_검색한다() {
        //given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("커스텀 예외를 처리하는 방법", "내용", Category.DISCUSSION.getValue(), List.of("Spring"), false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("커스텀예외를 처리하는 방법", "내용", Category.DISCUSSION.getValue(), List.of("Spring"), false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("예외를 커스텀하려면?", "내용", Category.QUESTION.getValue(), List.of("Spring"), false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("예외를커스텀하려면?", "내용", Category.QUESTION.getValue(), List.of("Spring"), false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("제목", "예외 어떻게 커스텀하죠 ㅠㅠ", Category.QUESTION.getValue(), List.of("Spring"), false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("제목", "예외 어떻게커스텀하죠 ㅠㅠ", Category.QUESTION.getValue(), List.of("Spring"), false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("제목", "예외는 이렇게 커스텀 하면 됩니다.", Category.DISCUSSION.getValue(), List.of("Spring"),
                        false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("제목", "예외는 이렇게 커스텀하면 됩니다.", Category.DISCUSSION.getValue(), List.of("Spring"),
                        false));

        //when
        int size = 4;
        String searchText = "커스텀";
        ArticlePageResponse firstPage = 게시글을_제목과_내용으로_처음_검색한다(size, searchText);
        ArticlePageResponse secondPage = 게시글을_제목과_내용으로_검색한다(firstPage.getArticles().get(size - 1).getId(), size,
                searchText);

        //then
        assertAll(
                () -> assertThat(firstPage.hasNext()).isTrue(),
                () -> assertThat(firstPage.getArticles()).hasSize(4),
                () -> assertThat(secondPage.hasNext()).isFalse(),
                () -> assertThat(secondPage.getArticles()).hasSize(4),
                () -> firstPage.getArticles()
                        .forEach(article -> assertThat(article.getTitle()).isEqualTo("제목")),
                () -> secondPage.getArticles()
                        .forEach(article -> assertThat(article.getContent()).isEqualTo("내용"))
        );
    }

    @Test
    void 유저_이름을_이용하여_게시글을_검색한다() {
        //given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("커스텀 예외를 처리하는 방법", "내용", Category.DISCUSSION.getValue(), List.of("Spring"), false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("커스텀예외를 처리하는 방법", "내용", Category.DISCUSSION.getValue(), List.of("Spring"), false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("예외를 커스텀하려면?", "내용", Category.QUESTION.getValue(), List.of("Spring"), true));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("예외를커스텀하려면?", "내용", Category.QUESTION.getValue(), List.of("Spring"), true));

        //when
        Long cursorId = null;
        int size = 4;
        String author = 주디.getName();
        ArticlePageResponse pageResponse = 게시글을_유저이름으로_검색한다(cursorId, size, author);

        assertAll(
                () -> assertThat(pageResponse.hasNext()).isFalse(),
                () -> assertThat(pageResponse.getArticles()).hasSize(2),
                () -> assertThat(pageResponse.getArticles().get(1).getTitle()).isEqualTo("커스텀 예외를 처리하는 방법"),
                () -> assertThat(pageResponse.getArticles().get(0).getTitle()).isEqualTo("커스텀예외를 처리하는 방법")
        );
    }

    @Test
    void 하나의_해시태그로_게시글들을_검색한다() {
        //given
        AccessTokenResponse tokenResponse = 로그인을_한다(레넌);
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("커스텀 예외를 처리하는 방법", "내용", Category.DISCUSSION.getValue(), List.of("Spring", "Java"),
                        false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("커스텀예외를 처리하는 방법", "내용", Category.DISCUSSION.getValue(), List.of("JAVA", "SPRING"),
                        false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("예외를 커스텀하려면?", "내용", Category.QUESTION.getValue(), List.of("Spring"), false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("예외를커스텀하려면?", "내용", Category.QUESTION.getValue(), List.of("Spring"), false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("제목", "예외 어떻게 커스텀하죠 ㅠㅠ", Category.QUESTION.getValue(), List.of("REACT"), false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("제목", "예외 어떻게커스텀하죠 ㅠㅠ", Category.QUESTION.getValue(), List.of("REACT", "SpRIng"),
                        false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("제목", "예외는 이렇게 커스텀 하면 됩니다.", Category.DISCUSSION.getValue(),
                        List.of("Spring", "Exception"),
                        false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("제목", "예외는 이렇게 커스텀하면 됩니다.", Category.DISCUSSION.getValue(), List.of("Spring"),
                        false));

        //when
        Long cursorId = null;
        int size = 4;
        ExtractableResponse<Response> response = 해시태그로_게시글들을_검색한다(cursorId, size, "spring");
        ArticlePageResponse articlesResponse = response.as(ArticlePageResponse.class);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlesResponse.getArticles()).hasSize(4),
                () -> assertThat(articlesResponse.hasNext()).isTrue()
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"'spring,react', 8", "'java,react', 4", "'exception,react', 3", "'exception, hi', 1", "'', 0"})
    void 여러개의_해시태그로_게시글들을_조회한다(String tags, int expected) {
        //given
        AccessTokenResponse tokenResponse = 로그인을_한다(레넌);
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("커스텀 예외를 처리하는 방법", "내용", Category.DISCUSSION.getValue(), List.of("Spring", "Java"),
                        false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("커스텀예외를 처리하는 방법", "내용", Category.DISCUSSION.getValue(), List.of("JAVA", "SPRING"),
                        false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("예외를 커스텀하려면?", "내용", Category.QUESTION.getValue(), List.of("Spring"), false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("예외를커스텀하려면?", "내용", Category.QUESTION.getValue(), List.of("Spring"), false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("제목", "예외 어떻게 커스텀하죠 ㅠㅠ", Category.QUESTION.getValue(), List.of("REACT"), false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("제목", "예외 어떻게커스텀하죠 ㅠㅠ", Category.QUESTION.getValue(), List.of("REACT", "SpRIng"),
                        false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("제목", "예외는 이렇게 커스텀 하면 됩니다.", Category.DISCUSSION.getValue(),
                        List.of("Spring", "Exception"),
                        false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("제목", "예외는 이렇게 커스텀하면 됩니다.", Category.DISCUSSION.getValue(), List.of("Spring"),
                        false));

        //when
        Long cursorId = null;
        int size = 8;
        ExtractableResponse<Response> response = 해시태그로_게시글들을_검색한다(cursorId, size, tags);
        ArticlePageResponse articlesResponse = response.as(ArticlePageResponse.class);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlesResponse.getArticles()).hasSize(expected)
        );
    }

    @Test
    void 로그인_한후_해시태그로_게시글들을_검색한다() {
        //given
        AccessTokenResponse tokenResponse = 로그인을_한다(레넌);
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("커스텀 예외를 처리하는 방법", "내용", Category.DISCUSSION.getValue(), List.of("Spring", "Java"),
                        false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("커스텀예외를 처리하는 방법", "내용", Category.DISCUSSION.getValue(), List.of("JAVA", "SPRING"),
                        false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("예외를 커스텀하려면?", "내용", Category.QUESTION.getValue(), List.of("Spring"), false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("예외를커스텀하려면?", "내용", Category.QUESTION.getValue(), List.of("Spring"), false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("제목", "예외 어떻게 커스텀하죠 ㅠㅠ", Category.QUESTION.getValue(), List.of("REACT"), false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("제목", "예외 어떻게커스텀하죠 ㅠㅠ", Category.QUESTION.getValue(), List.of("REACT", "SpRIng"),
                        false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("제목", "예외는 이렇게 커스텀 하면 됩니다.", Category.DISCUSSION.getValue(),
                        List.of("Spring", "Exception"),
                        false));
        특정_게시글을_등록한다(tokenResponse,
                new ArticleRequest("제목", "예외는 이렇게 커스텀하면 됩니다.", Category.DISCUSSION.getValue(), List.of("Spring"),
                        false));

        //when
        Long cursorId = null;
        int size = 4;
        ExtractableResponse<Response> response = 로그인_후_해시태그로_게시글들을_검색한다(tokenResponse, cursorId, size, "spring");
        ArticlePageResponse articlesResponse = response.as(ArticlePageResponse.class);

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlesResponse.getArticles()).hasSize(4),
                () -> assertThat(articlesResponse.hasNext()).isTrue()
        );
    }

    @Test
    void 토론_게시글을_추천순으로_조회할때_다음_게시글이_있고_페이지의_크기만큼_조회한다() {
        //given
        AccessTokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시글1 = 토론_게시글을_기명으로_등록한다(엑세스토큰);
        게시글을_추천한다(엑세스토큰, 게시글1);
        게시글을_추천한다(로그인을_한다(슬로), 게시글1);

        ArticleIdResponse 게시글2 = 토론_게시글을_기명으로_등록한다(엑세스토큰);
        게시글을_추천한다(엑세스토큰, 게시글2);

        토론_게시글을_기명으로_등록한다(엑세스토큰);
        //when

        ExtractableResponse<Response> response = 게시글_전체를_추천순으로_조회한다(Category.DISCUSSION.getValue(), null,
                null, 2);
        ArticlePageResponse articlePageResponse = response.as(ArticlePageResponse.class);

        List<Long> ids = articlePageResponse.getArticles().stream()
                .map(ArticlePreviewResponse::getId)
                .collect(Collectors.toList());

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlePageResponse.hasNext()).isTrue(),
                () -> assertThat(ids.containsAll(List.of(1L, 2L))).isTrue()
        );
    }

    @Test
    void 토론_게시글을_추천순으로_조회하고_다음_게시글이_없으면_hasNext는_false이고_게시글_개수만큼_조회한다() {
        //given
        AccessTokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시글1 = 토론_게시글을_기명으로_등록한다(엑세스토큰);
        게시글을_추천한다(엑세스토큰, 게시글1);
        게시글을_추천한다(로그인을_한다(슬로), 게시글1);

        ArticleIdResponse 게시글2 = 토론_게시글을_기명으로_등록한다(엑세스토큰);
        게시글을_추천한다(엑세스토큰, 게시글2);

        토론_게시글을_기명으로_등록한다(엑세스토큰);
        //when

        ExtractableResponse<Response> response = 게시글_전체를_추천순으로_조회한다(Category.DISCUSSION.getValue(), null,
                null, 5);
        ArticlePageResponse articlePageResponse = response.as(ArticlePageResponse.class);

        List<Long> ids = articlePageResponse.getArticles().stream()
                .map(ArticlePreviewResponse::getId)
                .collect(Collectors.toList());

        //then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(articlePageResponse.hasNext()).isFalse(),
                () -> assertThat(ids.containsAll(List.of(1L, 2L, 3L))).isTrue()
        );
    }

    @Test
    void 토론_게시글을_추천순으로_조회하고_다음_페이지의_게시글을_조회한다() {
        //given
        AccessTokenResponse 엑세스토큰 = 로그인을_한다(주디);
        ArticleIdResponse 게시글1 = 토론_게시글을_기명으로_등록한다(엑세스토큰);
        게시글을_추천한다(엑세스토큰, 게시글1);
        게시글을_추천한다(로그인을_한다(슬로), 게시글1);

        ArticleIdResponse 게시글2 = 토론_게시글을_기명으로_등록한다(엑세스토큰);
        게시글을_추천한다(엑세스토큰, 게시글2);

        ArticleIdResponse 게시글3 = 토론_게시글을_기명으로_등록한다(엑세스토큰);

        //when
        ArticlePageResponse response = 게시글_전체를_추천순으로_조회한다(Category.DISCUSSION.getValue(), null, null, 2).as(
                ArticlePageResponse.class);
        ArticlePageResponse articlePageResponse = 게시글_전체를_추천순으로_조회한다(Category.DISCUSSION.getValue(), 게시글2.getId(),
                response.getArticles().get(1).getLikeCount(), 2).as(ArticlePageResponse.class);

        //then
        assertAll(
                () -> assertThat(articlePageResponse.hasNext()).isFalse(),
                () -> assertThat(articlePageResponse.getArticles().get(0).getId()).isEqualTo(게시글3.getId())
        );
    }

    @Test
    void 투표가_있는_토론_게시글을_삭제할_수_있다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(기론);
        ArticleIdResponse articleIdResponse = 기명으로_게시글을_등록한다(tokenResponse, Category.DISCUSSION).as(
                ArticleIdResponse.class);
        투표를_생성한다(tokenResponse, articleIdResponse.getId(),
                new VoteCreateRequest(Set.of("1번 주제", "2번 주제"), LocalDateTime.now().plusDays(7)));

        // when
        ExtractableResponse<Response> response = 로그인_후_게시글을_삭제한다(tokenResponse, articleIdResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void 투표가_있고_투표를_한_토론_게시글을_삭제할_수_있다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(기론);
        ArticleIdResponse articleIdResponse = 기명으로_게시글을_등록한다(tokenResponse, Category.DISCUSSION).as(
                ArticleIdResponse.class);
        투표를_생성한다(tokenResponse, articleIdResponse.getId(),
                new VoteCreateRequest(Set.of("1번 주제", "2번 주제"), LocalDateTime.now().plusDays(7)));
        투표를_한다(tokenResponse, articleIdResponse.getId(), new SelectVoteItemIdRequest(1L));
        // when
        ExtractableResponse<Response> response = 로그인_후_게시글을_삭제한다(tokenResponse, articleIdResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
