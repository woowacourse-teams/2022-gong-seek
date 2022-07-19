package com.woowacourse.gongseek.comment.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.gongseek.auth.infra.JwtTokenProvider;
import com.woowacourse.gongseek.comment.application.CommentService;
import com.woowacourse.gongseek.comment.presentation.dto.CommentRequest;
import com.woowacourse.gongseek.comment.presentation.dto.CommentResponse;
import com.woowacourse.gongseek.config.RestDocsConfig;
import com.woowacourse.gongseek.member.presentation.dto.MemberDto;
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

@DisplayName("댓글 문서화")
@AutoConfigureRestDocs
@WebMvcTest(CommentController.class)
@Import(RestDocsConfig.class)
class CommentControllerTest {

    @MockBean
    private CommentService commentService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 댓글_생성_API_문서화() throws Exception {
        CommentRequest request = new CommentRequest("content");

        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(jwtTokenProvider.getPayload(any())).willReturn("1");

        ResultActions results = mockMvc.perform(post("/api/articles/{articleId}/comments", 1)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("comment-create",
                                requestFields(
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                                )
                        )
                );
    }

    @Test
    void 댓글_조회_API_문서화() throws Exception {
        CommentResponse commentResponse = new CommentResponse(
                1L, "content",
                new MemberDto("authorName", "authorAvatarUrl"),
                true, LocalDateTime.now()
        );
        given(commentService.findByArticleId(any(), any())).willReturn(List.of(commentResponse));

        mockMvc.perform(get("/api/articles/{articleId}/comments", 1)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("comment-find-all-by-article-id",
                                responseFields(
                                        fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("댓글 아이디"),
                                        fieldWithPath("[].content").type(JsonFieldType.STRING).description("댓글 내용"),
                                        fieldWithPath("[].author.name").type(JsonFieldType.STRING)
                                                .description("작성자 이름"),
                                        fieldWithPath("[].author.avatarUrl").type(JsonFieldType.STRING)
                                                .description("작성자 이미지"),
                                        fieldWithPath("[].isAuthor").type(JsonFieldType.BOOLEAN).description("작성자 여부"),
                                        fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("생성 날짜")
                                )
                        )
                );
    }

    @Test
    void 댓글_수정_API_문서화() throws Exception {

    }

    @Test
    void 댓글_삭제_API_문서화() throws Exception {

    }

}