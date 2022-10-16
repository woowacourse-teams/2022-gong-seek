package com.woowacourse.gongseek.like.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.gongseek.support.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.ResultActions;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("추천 문서화")
class LikeControllerTest extends ControllerTest {

    @Test
    void 추천_API_문서화() throws Exception {
        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);

        ResultActions results = mockMvc.perform(post("/api/articles/{articleId}/like", 1L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer Token")
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("like-article",
                                requestHeaders(
                                        headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                                )
                        )
                );
    }

    @Test
    void 추천_취소_API_문서화() throws Exception {
        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);

        ResultActions results = mockMvc.perform(delete("/api/articles/{articleId}/like", 1L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer Token")
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("unlike-article",
                                requestHeaders(
                                        headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                                )
                        )
                );
    }
}
