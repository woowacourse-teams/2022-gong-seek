package com.woowacourse.gongseek.vote.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
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
import com.woowacourse.gongseek.config.RestDocsConfig;
import com.woowacourse.gongseek.vote.application.VoteService;
import com.woowacourse.gongseek.vote.presentation.dto.SelectVoteItemIdRequest;
import com.woowacourse.gongseek.vote.presentation.dto.VoteCreateRequest;
import com.woowacourse.gongseek.vote.presentation.dto.VoteCreateResponse;
import com.woowacourse.gongseek.vote.presentation.dto.VoteItemResponse;
import com.woowacourse.gongseek.vote.presentation.dto.VoteResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
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

@DisplayName("투표 문서화")
@AutoConfigureRestDocs
@WebMvcTest(VoteController.class)
@Import(RestDocsConfig.class)
class VoteControllerTest {

    @MockBean
    private VoteService voteService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 투표_생성_API_문서화() throws Exception {
        VoteCreateResponse response = new VoteCreateResponse(1L);
        VoteCreateRequest request = new VoteCreateRequest(Set.of("오점 제육", "오점 편의점", "오점 서브웨이"),
                LocalDateTime.now().plusDays(5));
        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);
        given(jwtTokenProvider.getAccessTokenPayload(any())).willReturn("1");
        given(voteService.create(any(), any(), any())).willReturn(response);

        ResultActions results = mockMvc.perform(post("/api/articles/{articleId}/votes", response.getArticleId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("UTF-8"));

        results.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("vote-create",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        ),
                        requestFields(
                                fieldWithPath("items[]").type(JsonFieldType.ARRAY).description("투표 항목 내용들"),
                                fieldWithPath("expiryDate").type(JsonFieldType.STRING)
                                        .description("투표 종료 날짜(최대 생성일로부터 일주일까지 가능)")
                        ),
                        responseFields(
                                fieldWithPath("articleId").type(JsonFieldType.NUMBER).description("게시물 식별자")
                        )
                ));
    }

    @Test
    void 투표_조회_API_문서화() throws Exception {
        List<VoteItemResponse> voteItemResponses = List.of(new VoteItemResponse(1L, "오점 제육", 10),
                new VoteItemResponse(2L, "오점 서브웨이", 2));
        VoteResponse response = new VoteResponse(1L, voteItemResponses, 1L, false);
        VoteCreateRequest request = new VoteCreateRequest(Set.of("오점 제육", "오점 편의점", "오점 서브웨이"),
                LocalDateTime.now().plusDays(5));

        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);
        given(jwtTokenProvider.getAccessTokenPayload(any())).willReturn("1");
        given(voteService.getOne(any(), any())).willReturn(response);

        ResultActions results = mockMvc.perform(get("/api/articles/{articleId}/votes", response.getArticleId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("vote-get",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        ),

                        responseFields(
                                fieldWithPath("articleId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("voteItems[].id").type(JsonFieldType.NUMBER).description("투표 항목 식별자"),
                                fieldWithPath("voteItems[].content").type(JsonFieldType.STRING).description("투표 항목 이름"),
                                fieldWithPath("voteItems[].amount").type(JsonFieldType.NUMBER).description("득표 수"),
                                fieldWithPath("votedItemId").type(JsonFieldType.NUMBER)
                                        .description("로그인한 유저가 투표한 번호, 투표를 안했으면 null반환").optional(),
                                fieldWithPath("expired").type(JsonFieldType.BOOLEAN).description("투표 기간이 종료되었으면 true")
                        )
                ));
    }

    @Test
    void 투표_하기_API_문서화() throws Exception {
        SelectVoteItemIdRequest request = new SelectVoteItemIdRequest(1L);

        given(jwtTokenProvider.isValidAccessToken(any())).willReturn(true);
        given(jwtTokenProvider.getAccessTokenPayload(any())).willReturn("1");
        doNothing().when(voteService).doVote(any(), any(), any());

        ResultActions results = mockMvc.perform(post("/api/articles/{articleId}/votes/do", 1L)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("UTF-8"));

        results.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(document("vote-do",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer + 토큰")
                        ),
                        requestFields(
                                fieldWithPath("voteItemId").type(JsonFieldType.NUMBER).description("투표한 항목의 식별자")
                        )
                ));
    }
}
