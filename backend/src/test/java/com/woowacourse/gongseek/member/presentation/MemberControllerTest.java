package com.woowacourse.gongseek.member.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.gongseek.auth.infra.JwtTokenProvider;
import com.woowacourse.gongseek.auth.presentation.dto.LoginMember;
import com.woowacourse.gongseek.config.RestDocsConfig;
import com.woowacourse.gongseek.member.application.MemberService;
import com.woowacourse.gongseek.member.presentation.dto.AuthorDto;
import com.woowacourse.gongseek.member.presentation.dto.MemberDto;
import com.woowacourse.gongseek.member.presentation.dto.MyPageArticleResponse;
import com.woowacourse.gongseek.member.presentation.dto.MyPageArticlesResponse;
import com.woowacourse.gongseek.member.presentation.dto.MyPageCommentResponse;
import com.woowacourse.gongseek.member.presentation.dto.MyPageCommentsResponse;
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
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SuppressWarnings("NonAsciiCharacters")
@DisplayName("회원 조회 문서화")
@AutoConfigureRestDocs
@WebMvcTest(MemberController.class)
@Import(RestDocsConfig.class)
public class MemberControllerTest {

    @MockBean
    private MemberService memberService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 로그인한_사용자일때_회원_조회_API_문서화() throws Exception {
        MemberDto memberDto = new MemberDto("레넌", "https://avatars.githubusercontent.com/u/70756680?v=4");
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(jwtTokenProvider.getPayload(any())).willReturn("1");
        given(memberService.getOne(new LoginMember(any()))).willReturn(memberDto);

        ResultActions results = mockMvc.perform(get("/api/members/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("member-get-one",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        ),
                        responseFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("avatarUrl").type(JsonFieldType.STRING).description("프로필 이미지")
                        )
                ));
    }

    @Test
    void 로그인한_사용자일때_회원_게시글_조회_API_문서화() throws Exception {
        MyPageArticlesResponse myPageArticlesResponse = new MyPageArticlesResponse(
                List.of(
                        new MyPageArticleResponse(1L, "title1", "question1", 10, LocalDateTime.now(),
                                LocalDateTime.now(), 100),
                        new MyPageArticleResponse(1L, "title2", "question2", 20, LocalDateTime.now(),
                                LocalDateTime.now(), 200))
        );
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(jwtTokenProvider.getPayload(any())).willReturn("1");
        given(memberService.getArticles(new LoginMember(any()))).willReturn(myPageArticlesResponse);

        ResultActions results = mockMvc.perform(get("/api/members/me/articles")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("member-get-articles",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        ),
                        responseFields(
                                fieldWithPath("articles[].id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                fieldWithPath("articles[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("articles[].category").type(JsonFieldType.STRING).description("카테고리"),
                                fieldWithPath("articles[].commentCount").type(JsonFieldType.NUMBER).description("댓글 수"),
                                fieldWithPath("articles[].createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                fieldWithPath("articles[].updatedAt").type(JsonFieldType.STRING).description("수정 날짜"),
                                fieldWithPath("articles[].views").type(JsonFieldType.NUMBER).description("조회 수")
                        )
                ));
    }

    @Test
    void 로그인한_사용자일때_회원_댓글_조회_API_문서화() throws Exception {
        MyPageCommentsResponse myPageCommentsResponse = new MyPageCommentsResponse(
                List.of(
                        new MyPageCommentResponse(1L, "댓글1", LocalDateTime.now(), LocalDateTime.now()),
                        new MyPageCommentResponse(1L, "댓글2", LocalDateTime.now(), LocalDateTime.now()))
        );
        given(jwtTokenProvider.validateToken(any())).willReturn(true);
        given(jwtTokenProvider.getPayload(any())).willReturn("1");
        given(memberService.getComments(new LoginMember(any()))).willReturn(myPageCommentsResponse);

        ResultActions results = mockMvc.perform(get("/api/members/me/comments")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("member-get-comments",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        ),
                        responseFields(
                                fieldWithPath("comments[].id").type(JsonFieldType.NUMBER).description("댓글 아이디"),
                                fieldWithPath("comments[].content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("comments[].createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                fieldWithPath("comments[].updatedAt").type(JsonFieldType.STRING).description("수정 날짜")
                        )
                ));
    }
}
