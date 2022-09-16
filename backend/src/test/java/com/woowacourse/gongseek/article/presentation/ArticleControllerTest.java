package com.woowacourse.gongseek.article.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.gongseek.article.application.ArticleService;
import com.woowacourse.gongseek.article.domain.Category;
import com.woowacourse.gongseek.article.presentation.dto.ArticleIdResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticlePageResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticlePreviewResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleResponse;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateRequest;
import com.woowacourse.gongseek.article.presentation.dto.ArticleUpdateResponse;
import com.woowacourse.gongseek.auth.infra.JwtTokenProvider;
import com.woowacourse.gongseek.config.RestDocsConfig;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
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
    void 질문_게시글_생성_API_문서화() throws Exception {
        ArticleIdResponse response = new ArticleIdResponse(1L);
        ArticleRequest request = new ArticleRequest("title", "content", "question", List.of("Spring"), false, 1L);

        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);
        given(jwtTokenProvider.getAccessTokenPayload(any())).willReturn("1");
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
    void 로그인한_사용자일때_기명_게시글_단건_조회_API_문서화() throws Exception {
        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);

        ArticleResponse response = new ArticleResponse(
                "title",
                List.of("SPRING", "JAVA"),
                new AuthorDto("rennon", "avatar.com"),
                "content",
                false,
                1,
                false,
                false,
                0L,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        given(articleService.getOne(any(), any())).willReturn(response);

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
                                fieldWithPath("tag").type(JsonFieldType.ARRAY).description("해시태그"),
                                fieldWithPath("author.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("author.avatarUrl").type(JsonFieldType.STRING).description("프로필"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("views").type(JsonFieldType.NUMBER).description("조회수"),
                                fieldWithPath("isAuthor").type(JsonFieldType.BOOLEAN).description("작성자이면 true"),
                                fieldWithPath("hasVote").type(JsonFieldType.BOOLEAN).description("투표가 있으면 true"),
                                fieldWithPath("isLike").type(JsonFieldType.BOOLEAN).description("추천 여부"),
                                fieldWithPath("likeCount").type(JsonFieldType.NUMBER).description("추천 수"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("수정 날짜"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 날짜")
                        )
                ));
    }

    @Test
    void 로그인한_사용자일때_익명_게시글_단건_조회_API_문서화() throws Exception {
        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);

        ArticleResponse response = new ArticleResponse(
                "title",
                List.of("SPRING", "JAVA"),
                new AuthorDto("익명",
                        "https://raw.githubusercontent.com/woowacourse-teams/2022-gong-seek/develop/frontend/src/assets/gongseek.png"),
                "content",
                false,
                1,
                false,
                false,
                0L,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        given(articleService.getOne(any(), any())).willReturn(response);

        ResultActions results = mockMvc.perform(get("/api/articles/{id}", 1L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("article-find-one-anonymous-login",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        ),
                        responseFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("tag").type(JsonFieldType.ARRAY).description("해시태그"),
                                fieldWithPath("author.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("author.avatarUrl").type(JsonFieldType.STRING).description("프로필"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("views").type(JsonFieldType.NUMBER).description("조회수"),
                                fieldWithPath("isAuthor").type(JsonFieldType.BOOLEAN).description("작성자이면 true"),
                                fieldWithPath("hasVote").type(JsonFieldType.BOOLEAN).description("투표가 있으면 true"),
                                fieldWithPath("isLike").type(JsonFieldType.BOOLEAN).description("추천 여부"),
                                fieldWithPath("likeCount").type(JsonFieldType.NUMBER).description("추천 수"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("수정 날짜")
                        )
                ));
    }

    @Test
    void 로그인_안한_사용자일때_기명_게시글_단건_조회_API_문서화() throws Exception {
        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);

        ArticleResponse response = new ArticleResponse(
                "title",
                List.of("SPRING", "JAVA"),
                new AuthorDto("rennon", "avatar.com"),
                "content",
                false,
                1,
                false,
                false,
                0L,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        given(articleService.getOne(any(), any())).willReturn(response);

        ResultActions results = mockMvc.perform(get("/api/articles/{id}", 1L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("article-find-one-not-login",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        ),
                        responseFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("tag").type(JsonFieldType.ARRAY).description("해시태그"),
                                fieldWithPath("author.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("author.avatarUrl").type(JsonFieldType.STRING).description("프로필"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("views").type(JsonFieldType.NUMBER).description("조회수"),
                                fieldWithPath("isAuthor").type(JsonFieldType.BOOLEAN).description("작성자이면 true"),
                                fieldWithPath("hasVote").type(JsonFieldType.BOOLEAN).description("투표가 있으면 true"),
                                fieldWithPath("isLike").type(JsonFieldType.BOOLEAN).description("추천 여부"),
                                fieldWithPath("likeCount").type(JsonFieldType.NUMBER).description("추천 수"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("수정 날짜"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 날짜")
                        )
                ));
    }

    @Test
    void 게시글_수정_API_문서화() throws Exception {
        ArticleUpdateRequest request = new ArticleUpdateRequest("제목 바꿀께요", "내용 수정합니다~~~", List.of("JAVA"));
        ArticleUpdateResponse articleUpdateResponse = new ArticleUpdateResponse(1L, Category.QUESTION.getValue());
        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);
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
                                fieldWithPath("title").type(JsonFieldType.STRING).description("수정할 게시글 제목"),
                                fieldWithPath("tag").type(JsonFieldType.ARRAY).description("수정할 게시글 해시태그"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("category").type(JsonFieldType.STRING).description("카테고리")
                        )
                ));
    }

    @Test
    void 게시글_삭제_API_문서화() throws Exception {
        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);
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

    @Test
    void 게시글_전체_조회_문서화() throws Exception {
        ArticlePreviewResponse articlePreviewResponse1 = new ArticlePreviewResponse(1L, "제목", List.of("SPRING"),
                new AuthorDto("기론", "프로필 이미지 url"),
                "내용입니다", Category.QUESTION.getValue(), 3, 2, false, 0L, LocalDateTime.now());

        ArticlePreviewResponse articlePreviewResponse2 = new ArticlePreviewResponse(2L, "제목2", List.of("SPRING"),
                new AuthorDto("기론2", "프로필2 이미지 url"),
                "내용입니다22", Category.DISCUSSION.getValue(), 10, 5, false, 0L, LocalDateTime.now());

        ArticlePageResponse response = new ArticlePageResponse(
                List.of(articlePreviewResponse1, articlePreviewResponse2), false);

        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);
        given(jwtTokenProvider.getAccessTokenPayload(any())).willReturn("1");
        given(articleService.getAll(anyLong(), anyInt(), any(), any(), any(), any())).willReturn(response);

        ResultActions results = mockMvc.perform(get("/api/articles")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .param("category", Category.DISCUSSION.getValue())
                .param("sort", "latest")
                .param("cursorId", "1")
                .param("cursorViews", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("article-get-all",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        ),
                        requestParameters(
                                parameterWithName("category").description("조회할 카테고리(all, discussion, question"),
                                parameterWithName("sort").description("정렬 기준(latest-최신순, views-조회순)"),
                                parameterWithName("cursorId").description("시작은 null, 마지막으로 조회한 게시글 식별자").optional(),
                                parameterWithName("cursorViews").description("마지막으로 조회한 게시글 조회수").optional(),
                                parameterWithName("size").description("가져올 게시글 개수")
                        ),
                        responseFields(
                                fieldWithPath("articles[].id").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("articles[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("articles[].tag").type(JsonFieldType.ARRAY).description("게시글 해시태그"),
                                fieldWithPath("articles[].author.name").type(JsonFieldType.STRING)
                                        .description("게시글 작성자 이름"),
                                fieldWithPath("articles[].author.avatarUrl").type(JsonFieldType.STRING)
                                        .description("게시글 작성자 프로필 이미지"),
                                fieldWithPath("articles[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("articles[].category").type(JsonFieldType.STRING).description("게시글 종류"),
                                fieldWithPath("articles[].commentCount").type(JsonFieldType.NUMBER)
                                        .description("게시글 댓글 개수"),
                                fieldWithPath("articles[].createdAt").type(JsonFieldType.STRING)
                                        .description("게시글 생성 날짜"),
                                fieldWithPath("articles[].views").type(JsonFieldType.NUMBER).description("게시글 조회 수"),
                                fieldWithPath("articles[].isLike").type(JsonFieldType.BOOLEAN).description("추천 여부"),
                                fieldWithPath("articles[].likeCount").type(JsonFieldType.NUMBER).description("추천 수"),
                                fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN)
                                        .description("다음에 조회 할 게시글이 있으면 true")
                        )
                ));
    }

    @Test
    void 게시글_제목_내용_검색_문서화() throws Exception {
        ArticlePreviewResponse articlePreviewResponse1 = new ArticlePreviewResponse(1L, "제목", List.of("SPRING"),
                new AuthorDto("작성자1", "작성자1 이미지 url"),
                "내용", Category.QUESTION.getValue(), 3, 2, false, 0L, LocalDateTime.now());
        ArticlePreviewResponse articlePreviewResponse2 = new ArticlePreviewResponse(2L, "제목", List.of("SPRING"),
                new AuthorDto("작성자2", "작성자2 이미지 url"),
                "내용", Category.DISCUSSION.getValue(), 10, 5, false, 0L, LocalDateTime.now());
        ArticlePageResponse response = new ArticlePageResponse(
                List.of(articlePreviewResponse1, articlePreviewResponse2), false);

        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);
        given(jwtTokenProvider.getAccessTokenPayload(any())).willReturn("1");
        given(articleService.searchByText(anyLong(), any(), anyString(), any())).willReturn(response);

        ResultActions results = mockMvc.perform(get("/api/articles/search/text")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .param("cursorId", "1")
                .param("size", "10")
                .param("text", "제목")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("article-search-text",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        ),
                        requestParameters(
                                parameterWithName("cursorId").description("시작은 null, 마지막으로 조회한 게시글 식별자").optional(),
                                parameterWithName("size").description("가져올 게시글 개수"),
                                parameterWithName("text").description("검색할 Text(제목, 내용)")
                        ),
                        responseFields(
                                fieldWithPath("articles[].id").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("articles[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("articles[].tag").type(JsonFieldType.ARRAY).description("게시글 해시태그"),
                                fieldWithPath("articles[].author.name").type(JsonFieldType.STRING)
                                        .description("게시글 작성자 이름"),
                                fieldWithPath("articles[].author.avatarUrl").type(JsonFieldType.STRING)
                                        .description("게시글 작성자 프로필 이미지"),
                                fieldWithPath("articles[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("articles[].category").type(JsonFieldType.STRING).description("게시글 종류"),
                                fieldWithPath("articles[].commentCount").type(JsonFieldType.NUMBER)
                                        .description("게시글 댓글 개수"),
                                fieldWithPath("articles[].createdAt").type(JsonFieldType.STRING)
                                        .description("게시글 생성 날짜"),
                                fieldWithPath("articles[].views").type(JsonFieldType.NUMBER).description("게시글 조회 수"),
                                fieldWithPath("articles[].isLike").type(JsonFieldType.BOOLEAN).description("추천 여부"),
                                fieldWithPath("articles[].likeCount").type(JsonFieldType.NUMBER).description("추천 수"),
                                fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN)
                                        .description("다음에 조회 할 게시글이 있으면 true")
                        )
                ));
    }

    @Test
    void 게시글_작성자이름_검색_문서화() throws Exception {
        ArticlePreviewResponse articlePreviewResponse = new ArticlePreviewResponse(1L, "제목", List.of("SPRING"),
                new AuthorDto("작성자", "작성자1 이미지 url"),
                "내용", Category.QUESTION.getValue(), 3, 2, false, 0L, LocalDateTime.now());
        ArticlePageResponse response = new ArticlePageResponse(
                List.of(articlePreviewResponse), false);

        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);
        given(jwtTokenProvider.getAccessTokenPayload(any())).willReturn("1");
        given(articleService.searchByAuthor(anyLong(), any(), any(), any())).willReturn(response);

        ResultActions results = mockMvc.perform(get("/api/articles/search/author")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .param("cursorId", "1")
                .param("size", "10")
                .param("author", "작성자")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("article-search-author",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        ),
                        requestParameters(
                                parameterWithName("cursorId").description("시작은 null, 마지막으로 조회한 게시글 식별자").optional(),
                                parameterWithName("size").description("가져올 게시글 개수"),
                                parameterWithName("author").description("작성자 이름")
                        ),
                        responseFields(
                                fieldWithPath("articles[].id").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("articles[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("articles[].tag").type(JsonFieldType.ARRAY).description("게시글 해시태그"),
                                fieldWithPath("articles[].author.name").type(JsonFieldType.STRING)
                                        .description("게시글 작성자 이름"),
                                fieldWithPath("articles[].author.avatarUrl").type(JsonFieldType.STRING)
                                        .description("게시글 작성자 프로필 이미지"),
                                fieldWithPath("articles[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("articles[].category").type(JsonFieldType.STRING).description("게시글 종류"),
                                fieldWithPath("articles[].commentCount").type(JsonFieldType.NUMBER)
                                        .description("게시글 댓글 개수"),
                                fieldWithPath("articles[].createdAt").type(JsonFieldType.STRING)
                                        .description("게시글 생성 날짜"),
                                fieldWithPath("articles[].views").type(JsonFieldType.NUMBER).description("게시글 조회 수"),
                                fieldWithPath("articles[].isLike").type(JsonFieldType.BOOLEAN).description("추천 여부"),
                                fieldWithPath("articles[].likeCount").type(JsonFieldType.NUMBER).description("추천 수"),
                                fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN)
                                        .description("다음에 조회 할 게시글이 있으면 true")
                        )
                ));
    }

    @Test
    void 게시글_추천수_전체_조회_문서화() throws Exception {
        ArticlePreviewResponse articlePreviewResponse1 = new ArticlePreviewResponse(1L, "제목", List.of("SPRING"),
                new AuthorDto("기론", "프로필 이미지 url"),
                "내용입니다", Category.QUESTION.getValue(), 3, 2, false, 2L, LocalDateTime.now());

        ArticlePreviewResponse articlePreviewResponse2 = new ArticlePreviewResponse(2L, "제목2", List.of("SPRING"),
                new AuthorDto("기론2", "프로필2 이미지 url"),
                "내용입니다22", Category.DISCUSSION.getValue(), 10, 5, false, 1L, LocalDateTime.now());
        ArticlePageResponse response = new ArticlePageResponse(
                List.of(articlePreviewResponse1, articlePreviewResponse2), false);

        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);
        given(jwtTokenProvider.getAccessTokenPayload(any())).willReturn("1");

        given(articleService.getAllByLikes(anyLong(), anyLong(), any(), any(), any())).willReturn(response);

        ResultActions results = mockMvc.perform(get("/api/articles/likes")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .param("category", Category.DISCUSSION.getValue())
                .param("cursorId", "1")
                .param("cursorLikes", "1")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("article-get-all-likes",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        ),
                        requestParameters(
                                parameterWithName("category").description("조회할 카테고리(all, discussion, question"),
                                parameterWithName("cursorId").description("시작은 null, 마지막으로 조회한 게시글 식별자").optional(),
                                parameterWithName("cursorLikes").description("마지막으로 조회한 게시글의 추천수").optional(),
                                parameterWithName("size").description("가져올 게시글 개수")
                        ),
                        responseFields(
                                fieldWithPath("articles[].id").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("articles[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("articles[].tag").type(JsonFieldType.ARRAY).description("게시글 해시태그"),
                                fieldWithPath("articles[].author.name").type(JsonFieldType.STRING)
                                        .description("게시글 작성자 이름"),
                                fieldWithPath("articles[].author.avatarUrl").type(JsonFieldType.STRING)
                                        .description("게시글 작성자 프로필 이미지"),
                                fieldWithPath("articles[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("articles[].category").type(JsonFieldType.STRING).description("게시글 종류"),
                                fieldWithPath("articles[].commentCount").type(JsonFieldType.NUMBER)
                                        .description("게시글 댓글 개수"),
                                fieldWithPath("articles[].createdAt").type(JsonFieldType.STRING)
                                        .description("게시글 생성 날짜"),
                                fieldWithPath("articles[].views").type(JsonFieldType.NUMBER).description("게시글 조회 수"),
                                fieldWithPath("articles[].isLike").type(JsonFieldType.BOOLEAN).description("추천 여부"),
                                fieldWithPath("articles[].likeCount").type(JsonFieldType.NUMBER).description("추천 수"),
                                fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN)
                                        .description("다음에 조회 할 게시글이 있으면 true")
                        )
                ));
    }

    @Test
    void 해시태그로_게시글_조회_문서화() throws Exception {
        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);

        ArticlePreviewResponse articlePreviewResponse1 = new ArticlePreviewResponse(1L, "제목", List.of("SPRING"),
                new AuthorDto("작성자1", "작성자1 이미지 url"),
                "내용", Category.QUESTION.getValue(), 3, 2, false, 0L, LocalDateTime.now());
        ArticlePreviewResponse articlePreviewResponse2 = new ArticlePreviewResponse(2L, "제목",
                List.of("SPRING", "JAVA"),
                new AuthorDto("작성자2", "작성자2 이미지 url"),
                "내용", Category.DISCUSSION.getValue(), 10, 5, false, 0L, LocalDateTime.now());
        ArticlePageResponse response = new ArticlePageResponse(
                List.of(articlePreviewResponse1, articlePreviewResponse2), false);

        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);
        given(jwtTokenProvider.getAccessTokenPayload(any())).willReturn("1");
        given(articleService.searchByTag(anyLong(), any(), anyString(), any())).willReturn(response);

        ResultActions results = mockMvc.perform(get("/api/articles/search/tags")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .param("cursorId", "1")
                .param("size", "10")
                .param("tagsText", "spring")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("articles-search-by-tag",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        ),
                        requestParameters(
                                parameterWithName("cursorId").description("시작은 null, 마지막으로 조회한 게시글 식별자").optional(),
                                parameterWithName("size").description("가져올 게시글 개수"),
                                parameterWithName("tagsText").description("해시태그 파라미터")
                        ),
                        responseFields(
                                fieldWithPath("articles[].id").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("articles[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("articles[].tag").type(JsonFieldType.ARRAY).description("게시글 해시태그"),
                                fieldWithPath("articles[].author.name").type(JsonFieldType.STRING)
                                        .description("게시글 작성자 이름"),
                                fieldWithPath("articles[].author.avatarUrl").type(JsonFieldType.STRING)
                                        .description("게시글 작성자 프로필 이미지"),
                                fieldWithPath("articles[].content").type(JsonFieldType.STRING)
                                        .description("게시글 내용"),
                                fieldWithPath("articles[].category").type(JsonFieldType.STRING)
                                        .description("게시글 종류"),
                                fieldWithPath("articles[].commentCount").type(JsonFieldType.NUMBER)
                                        .description("게시글 댓글 개수"),
                                fieldWithPath("articles[].createdAt").type(JsonFieldType.STRING)
                                        .description("게시글 생성 날짜"),
                                fieldWithPath("articles[].views").type(JsonFieldType.NUMBER)
                                        .description("게시글 조회 수"),
                                fieldWithPath("articles[].isLike").type(JsonFieldType.BOOLEAN).description("추천 여부"),
                                fieldWithPath("articles[].likeCount").type(JsonFieldType.NUMBER)
                                        .description("추천 수"),
                                fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN)
                                        .description("다음에 조회 할 게시글이 있으면 true")
                        )
                ));
    }
}
