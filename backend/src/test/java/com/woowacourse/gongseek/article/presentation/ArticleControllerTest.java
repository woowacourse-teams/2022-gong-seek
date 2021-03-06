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
@DisplayName("질문 게시판 문서화")
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
    void 질문_게시물_생성_API_문서화() throws Exception {
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
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("category").type(JsonFieldType.STRING).description("카테고리")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("식별자")
                        )
                ));
    }

    @Test
    void 로그인한_사용자일때_게시물_단건_조회_API_문서화() throws Exception {
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
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        ),
                        responseFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("author.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("author.avatarUrl").type(JsonFieldType.STRING).description("프로필"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("views").type(JsonFieldType.NUMBER).description("조회수"),
                                fieldWithPath("isAuthor").type(JsonFieldType.BOOLEAN).description("작성자이면 true"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 날짜")
                        )
                ));
    }

    @Test
    void 로그인_안한_사용자일때_게시물_단건_조회_API_문서화() throws Exception {
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
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("author.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("author.avatarUrl").type(JsonFieldType.STRING).description("프로필"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("views").type(JsonFieldType.NUMBER).description("조회수"),
                                fieldWithPath("isAuthor").type(JsonFieldType.BOOLEAN).description("작성자이면 true"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 날짜")
                        )
                ));
    }

    @Test
    void 게시물_수정_API_문서화() throws Exception {
        ArticleUpdateRequest request = new ArticleUpdateRequest("제목 바꿀께요", "내용 수정합니다~~~");
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
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("수정할 게시물 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 게시물 내용")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시물 식별자"),
                                fieldWithPath("category").type(JsonFieldType.STRING).description("카테고리")
                        )
                ));
    }

    @Test
    void 게시물_삭제_API_문서화() throws Exception {
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
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        )
                ));
    }
}
