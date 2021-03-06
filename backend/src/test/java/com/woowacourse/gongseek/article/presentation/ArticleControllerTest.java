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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.gongseek.article.application.ArticleService;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateResponse;
import com.woowacourse.gongseek.auth.infra.JwtTokenProvider;
import com.woowacourse.gongseek.config.RestDocsConfig;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
import java.time.LocalDateTime;
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
@DisplayName("?????? ????????? ?????????")
@AutoConfigureRestDocs
@WebMvcTest(ArticleController.class)
@Import(RestDocsConfig.class)
class ArticleControllerTest {

    @MockBean
    private ArticleService articleService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void ??????_?????????_??????_API_?????????() throws Exception {
        ArticleIdResponse response = new ArticleIdResponse(1L);
        ArticleRequest request = new ArticleRequest("title", "content", "question");

        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(jwtTokenProvider.getPayload(any())).willReturn("1");
        given(articleService.save(any(), any())).willReturn(response);

        ResultActions results = mockMvc.perform(post("/api/articles")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("UTF-8"));

        results.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("article-create",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + ??????")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("category").type(JsonFieldType.STRING).description("????????????")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????????")
                        )
                ));
    }

    @Test
    void ????????????_???????????????_?????????_??????_??????_API_?????????() throws Exception {
        ArticleResponse response = new ArticleResponse(
                "title",
                new AuthorDto("rennon", "avatar.com"),
                "content",
                false,
                1,
                LocalDateTime.now()
        );
        given(articleService.findOne(any(), any())).willReturn(response);

        ResultActions results = mockMvc.perform(get("/api/articles/{id}", 1L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("article-find-one-login",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + ??????")
                        ),
                        responseFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("author.name").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("author.avatarUrl").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("views").type(JsonFieldType.NUMBER).description("?????????"),
                                fieldWithPath("isAuthor").type(JsonFieldType.BOOLEAN).description("??????????????? true"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("?????? ??????")
                        )
                ));
    }

    @Test
    void ?????????_??????_???????????????_?????????_??????_??????_API_?????????() throws Exception {
        ArticleResponse response = new ArticleResponse(
                "title",
                new AuthorDto("rennon", "avatar.com"),
                "content",
                false,
                1,
                LocalDateTime.now()
        );
        given(articleService.findOne(any(), any())).willReturn(response);

        ResultActions results = mockMvc.perform(get("/api/articles/{id}", 1L)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("article-find-one-not-login",
                        responseFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("author.name").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("author.avatarUrl").type(JsonFieldType.STRING).description("?????????"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("??????"),
                                fieldWithPath("views").type(JsonFieldType.NUMBER).description("?????????"),
                                fieldWithPath("isAuthor").type(JsonFieldType.BOOLEAN).description("??????????????? true"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("?????? ??????")
                        )
                ));
    }

    @Test
    void ?????????_??????_API_?????????() throws Exception {
        ArticleUpdateRequest request = new ArticleUpdateRequest("?????? ????????????", "?????? ???????????????~~~");
        ArticleUpdateResponse articleUpdateResponse = new ArticleUpdateResponse(1L, Category.QUESTION.getValue());
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(jwtTokenProvider.getPayload(any())).willReturn("1");
        given(articleService.update(any(), any(), any())).willReturn(articleUpdateResponse);

        ResultActions results = mockMvc.perform(put("/api/articles/{id}", 1L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("article-update",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + ??????")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("????????? ????????? ??????"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("????????? ????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                fieldWithPath("category").type(JsonFieldType.STRING).description("????????????")
                        )
                ));
    }

    @Test
    void ?????????_??????_API_?????????() throws Exception {
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(jwtTokenProvider.getPayload(any())).willReturn("1");
        doNothing().when(articleService).delete(any(), any());

        ResultActions results = mockMvc.perform(delete("/api/articles/{id}", 1L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("article-delete",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + ??????")
                        )
                ));
    }
}
