package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.acceptance.support.fixtures.ArticleFixtures.기명으로_게시글을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.fixtures.AuthFixtures.로그인을_한다;
import static com.woowacourse.gongseek.acceptance.support.fixtures.TagFixtures.모든_해시태그를_조회한다;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.레넌;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.주디;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.auth.presentation.dto.AccessTokenResponse;
import com.woowacourse.gongseek.tag.presentation.dto.TagsResponse;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
public class TagAcceptanceTest extends AcceptanceTest {

    @Test
    void 로그인한_회원이_모든_해시태그를_조회한다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        기명으로_게시글을_등록한다(tokenResponse, Category.QUESTION);

        // when
        ExtractableResponse<Response> response = 모든_해시태그를_조회한다(tokenResponse);
        TagsResponse 해시태그 = response.as(TagsResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(해시태그.getTag()).hasSize(1),
                () -> assertThat(해시태그.getTag().get(0)).isEqualTo("SPRING")
        );
    }

    @Test
    void 로그인_안한_회원이_모든_해시태그를_조회한다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(주디);
        기명으로_게시글을_등록한다(tokenResponse, Category.QUESTION);

        // when
        ExtractableResponse<Response> response = 모든_해시태그를_조회한다(new AccessTokenResponse(null));
        TagsResponse 해시태그 = response.as(TagsResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(해시태그.getTag()).hasSize(1),
                () -> assertThat(해시태그.getTag().get(0)).isEqualTo("SPRING")
        );
    }

    @Test
    void 같은_해시태그로_게시글을_등록해도_해시태그는_증가하지_않는다() {
        // given
        AccessTokenResponse tokenResponse = 로그인을_한다(레넌);
        기명으로_게시글을_등록한다(tokenResponse, Category.QUESTION);
        기명으로_게시글을_등록한다(tokenResponse, Category.QUESTION);

        // when
        ExtractableResponse<Response> response = 모든_해시태그를_조회한다(tokenResponse);
        TagsResponse 해시태그 = response.as(TagsResponse.class);

        // then
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()),
                () -> assertThat(해시태그.getTag()).hasSize(1)
        );
    }
}
