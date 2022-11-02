package com.woowacourse.gongseek.comment.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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

import com.woowacourse.gongseek.comment.application.dto.CommentRequest;
import com.woowacourse.gongseek.comment.application.dto.CommentResponse;
import com.woowacourse.gongseek.comment.application.dto.CommentUpdateRequest;
import com.woowacourse.gongseek.comment.application.dto.CommentsResponse;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
import com.woowacourse.gongseek.support.ControllerTest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("댓글 문서화")
class CommentControllerTest extends ControllerTest {

    @Test
    void 댓글_생성_API_문서화() throws Exception {
        CommentRequest request = new CommentRequest("content", false);

        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);

        ResultActions results = mockMvc.perform(post("/api/articles/{articleId}/comments", 1)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("comment-create",
                                requestHeaders(
                                        headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                                ),
                                requestFields(
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("isAnonymous").type(JsonFieldType.BOOLEAN).description("익명 여부")
                                )
                        )
                );
    }

    @Test
    void 댓글_조회_API_문서화() throws Exception {
        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);
        CommentResponse authorComment = new CommentResponse(
                1L,
                "content1",
                new AuthorDto("authorName", "authorAvatarUrl1"),
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        CommentResponse nonAuthorComment = new CommentResponse(
                2L,
                "content2",
                new AuthorDto("nonAuthorName", "authorAvatarUrl2"),
                false,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        CommentResponse anonymousComment = new CommentResponse(
                3L,
                "content3",
                new AuthorDto("익명",
                        "https://raw.githubusercontent.com/woowacourse-teams/2022-gong-seek/develop/frontend/src/assets/gongseek.png"),
                false,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        given(commentService.getAllByArticleId(any(), any())).willReturn(
                new CommentsResponse(List.of(authorComment, nonAuthorComment, anonymousComment)));

        mockMvc.perform(get("/api/articles/{articleId}/comments", 1)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("comment-find-all-by-article-id",
                                requestHeaders(
                                        headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("comments[].id").type(JsonFieldType.NUMBER).description("댓글 아이디"),
                                        fieldWithPath("comments[].content").type(JsonFieldType.STRING).description("댓글 내용"),
                                        fieldWithPath("comments[].author.name").type(JsonFieldType.STRING)
                                                .description("작성자 이름"),
                                        fieldWithPath("comments[].author.avatarUrl").type(JsonFieldType.STRING)
                                                .description("작성자 이미지"),
                                        fieldWithPath("comments[].isAuthor").type(JsonFieldType.BOOLEAN).description("작성자 여부"),
                                        fieldWithPath("comments[].createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                        fieldWithPath("comments[].updatedAt").type(JsonFieldType.STRING).description("수정 날짜")
                                )
                        )
                );
    }

    @Test
    void 댓글_수정_API_문서화() throws Exception {
        CommentUpdateRequest request = new CommentUpdateRequest("content");
        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);

        ResultActions results = mockMvc.perform(put("/api/articles/comments/{commentId}", 1)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("comment-update",
                                requestHeaders(
                                        headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                                ),
                                requestFields(
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                                )
                        )
                );
    }

    @Test
    void 댓글_삭제_API_문서화() throws Exception {
        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);

        mockMvc.perform(delete("/api/articles/comments/{commentId}", 1)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("comment-delete",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        )));
    }
}
