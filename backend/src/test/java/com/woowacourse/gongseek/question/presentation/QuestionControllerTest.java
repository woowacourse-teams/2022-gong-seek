package com.woowacourse.gongseek.question.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.gongseek.config.RestDocsConfig;
import com.woowacourse.gongseek.question.application.QuestionService;
import com.woowacourse.gongseek.question.presentation.dto.QuestionResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("질문 게시판 문서화")
@AutoConfigureRestDocs
@WebMvcTest(QuestionController.class)
@Import(RestDocsConfig.class)
class QuestionControllerTest {

    @MockBean
    private QuestionService questionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 질문_게시물_생성_API_문서화() throws Exception {
        QuestionResponse response = new QuestionResponse(1L, "제목입니당", "내용입니당");
        given(questionService.save(any())).willReturn(response);

        ResultActions results = mockMvc.perform(post("/api/articles/questions")
                .content(objectMapper.writeValueAsString(response))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("question-create",
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("생성된 게시물의 url")
                        )));
    }
}
