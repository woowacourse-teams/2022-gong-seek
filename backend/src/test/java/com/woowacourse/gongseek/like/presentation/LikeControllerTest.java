package com.woowacourse.gongseek.like.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.woowacourse.gongseek.auth.infra.JwtTokenProvider;
import com.woowacourse.gongseek.config.RestDocsConfig;
import com.woowacourse.gongseek.like.application.LikeService;
import com.woowacourse.gongseek.like.presentation.dto.LikeResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("추천 문서화")
@AutoConfigureRestDocs
@WebMvcTest(LikeController.class)
@Import(RestDocsConfig.class)
class LikeControllerTest {

    @MockBean
    private LikeService likeService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 추천_API_문서화() throws Exception {
        LikeResponse likeResponse = new LikeResponse(true, 1L);
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(likeService.likeArticle(any(), anyLong())).willReturn(likeResponse);

        ResultActions results = mockMvc.perform(post("/api/articles/{articleId}/like", 1L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer Token")
                .characterEncoding("UTF-8"));

        results.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("like-article",
                                requestHeaders(
                                        headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("isLike").type(JsonFieldType.BOOLEAN).description("추천 여부"),
                                        fieldWithPath("likeCount").type(JsonFieldType.NUMBER).description("추천 수")
                                )
                        )
                );
    }

    @Test
    void 추천_취소_API_문서화() throws Exception {
        LikeResponse likeResponse = new LikeResponse(false, 0L);
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(likeService.unlikeArticle(any(), anyLong())).willReturn(likeResponse);

        ResultActions results = mockMvc.perform(delete("/api/articles/{articleId}/unlike", 1L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer Token")
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("unlike-article",
                                requestHeaders(
                                        headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("isLike").type(JsonFieldType.BOOLEAN).description("추천 여부"),
                                        fieldWithPath("likeCount").type(JsonFieldType.NUMBER).description("추천 수")
                                )
                        )
                );
    }
}
