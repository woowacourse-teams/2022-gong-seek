package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.acceptance.support.ArticleFixtures.게시글을_등록한다;
import static com.woowacourse.gongseek.acceptance.support.AuthFixtures.로그인을_한다;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.슬로;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.vote.presentation.dto.VoteItemRequest;
import com.woowacourse.gongseek.vote.presentation.dto.VoteRequest;
import com.woowacourse.gongseek.vote.presentation.dto.VoteResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@Disabled
public class VoteAcceptanceTest extends AcceptanceTest {

    @Test
    void 토론게시물에서_투표를_생성한다() {
        TokenResponse tokenResponse = 로그인을_한다(슬로);
        Long articleId = 게시글을_등록한다(tokenResponse, Category.DISCUSSION.getValue()).getId();

        VoteItemRequest voteItemRequest = new VoteItemRequest(Set.of("DTO를 반환해야 한다.", "도메인을 반환해야 한다."));
        LocalDateTime expireDate = LocalDateTime.now().plusDays(1);

        ExtractableResponse<Response> response = 투표를_생성한다(articleId, new VoteRequest(voteItemRequest, expireDate));

        VoteResponse voteResponse = response.as(VoteResponse.class);
        assertAll(
                () -> assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value()),
                () -> assertThat(voteResponse.getArticleId()).isEqualTo(articleId)
        );
    }

    private ExtractableResponse<Response> 투표를_생성한다(Long articleId, VoteRequest voteRequest) {
        return RestAssured
                .given().log().all()
                .pathParam("articleId", articleId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .body(voteRequest)
                .post("/api/articles/{articleId}/votes")
                .then().log().all()
                .extract();
    }

    @Test
    void 질문게시물에서_투표를_생성하면_예외가_발생한다() {

    }
}
