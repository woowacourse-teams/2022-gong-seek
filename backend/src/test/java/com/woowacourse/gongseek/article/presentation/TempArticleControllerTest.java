package com.woowacourse.gongseek.article.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.gongseek.article.application.TempArticleService;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.TempArticleDetailResponse;
import com.woowacourse.gongseek.article.presentation.dto.TempArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.TempArticleResponse;
import com.woowacourse.gongseek.article.presentation.dto.TempArticlesResponse;
import com.woowacourse.gongseek.auth.infra.JwtTokenProvider;
import com.woowacourse.gongseek.config.RestDocsConfig;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("임시 게시글 문서화")
@AutoConfigureRestDocs
@WebMvcTest(TempArticleController.class)
@Import(RestDocsConfig.class)
class TempArticleControllerTest {

    @MockBean
    private TempArticleService tempArticleService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 임시_게시글_생성_API_문서화() throws Exception {
        TempArticleIdResponse response = new TempArticleIdResponse(1L);
        ArticleRequest request = new ArticleRequest("title", "content", "question", List.of("Spring"), false);
        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);
        given(jwtTokenProvider.getAccessTokenPayload(any())).willReturn("1");
        given(tempArticleService.createOrUpdate(any(), any())).willReturn(response);

        ResultActions results = mockMvc.perform(post("/api/temp-articles")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("UTF-8"));

        results.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("temp-article-create",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("category").type(JsonFieldType.STRING).description("카테고리"),
                                fieldWithPath("tag.[]").type(JsonFieldType.ARRAY).description("해시태그"),
                                fieldWithPath("isAnonymous").type(JsonFieldType.BOOLEAN).description("익명 여부"),
                                fieldWithPath("tempArticleId").type(JsonFieldType.NUMBER).description("임시 게시글 식별자")
                                        .optional()
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("식별자")
                        )
                ));
    }

    @Test
    void 임시_게시글_단건_조회_API_문서화() throws Exception {
        TempArticleDetailResponse response = new TempArticleDetailResponse(1L, "title", "content",
                Category.QUESTION.getValue(), List.of("spring"), false, LocalDateTime.now());
        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);
        given(tempArticleService.getOne(any(), any())).willReturn(response);

        ResultActions results = mockMvc.perform(get("/api/temp-articles/{tempArticleId}", 1L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("temp-article-find-one",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("임시 게시글 식별자"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("category").type(JsonFieldType.STRING).description("카테고리"),
                                fieldWithPath("tags").type(JsonFieldType.ARRAY).description("해시태그"),
                                fieldWithPath("isAnonymous").type(JsonFieldType.BOOLEAN).description("익명 여부"),
                                fieldWithPath("createAt").type(JsonFieldType.STRING).description("생성 날짜")
                        )
                ));
    }

    @Test
    void 임시_게시글_전체_조회_API_문서화() throws Exception {
        TempArticleResponse response1 = new TempArticleResponse(1L, "title1", LocalDateTime.now());
        TempArticleResponse response2 = new TempArticleResponse(2L, "title2", LocalDateTime.now());
        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);
        given(tempArticleService.getAll(any())).willReturn(new TempArticlesResponse(List.of(response1, response2)));

        ResultActions results = mockMvc.perform(get("/api/temp-articles")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("temp-article-find-all",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        ),
                        responseFields(
                                fieldWithPath("values[].id").type(JsonFieldType.NUMBER).description("임시 게시글 식별자"),
                                fieldWithPath("values[].title").type(JsonFieldType.STRING).description("임시 제목"),
                                fieldWithPath("values[].createAt").type(JsonFieldType.STRING).description("생성 날짜")
                        )
                ));
    }

    @Test
    void 임시_게시글_삭제_API_문서화() throws Exception {
        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);
        given(jwtTokenProvider.getRefreshTokenPayload(any())).willReturn("1");
        doNothing().when(tempArticleService).delete(any(), any());

        ResultActions results = mockMvc.perform(delete("/api/temp-articles/{tempArticleId}", 1L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer Token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("temp-article-delete",
                        requestHeaders(headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰"))));
    }
}
