package com.woowacourse.gongseek.auth.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woowacourse.gongseek.auth.application.AuthService;
import com.woowacourse.gongseek.auth.infra.JwtTokenProvider;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthCodeRequest;
import com.woowacourse.gongseek.auth.presentation.dto.OAuthLoginUrlResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import com.woowacourse.gongseek.auth.utils.CookieUtils;
import com.woowacourse.gongseek.config.RestDocsConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@DisplayName("로그인 문서화")
@AutoConfigureRestDocs
@WebMvcTest(AuthController.class)
@Import(RestDocsConfig.class)
class AuthControllerTest {

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 로그인_URL_조회_API_문서화() throws Exception {
        given(authService.getLoginUrl()).willReturn(new OAuthLoginUrlResponse("login url"));
        mockMvc.perform(get("/api/auth/github"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("login-url",
                                responseFields(
                                        fieldWithPath("url").type(JsonFieldType.STRING).description("로그인 요청 URL")
                                )
                        )
                );
    }

    @Test
    void 로그인_ACCESS_TOKEN_생성_API_문서화() throws Exception {
        OAuthCodeRequest request = new OAuthCodeRequest("code");
        given(authService.generateToken(any())).willReturn(new TokenResponse("refreshToken", "accessToken"));

        ResultActions results = mockMvc.perform(post("/api/auth/login")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("login-token",
                                requestFields(
                                        fieldWithPath("code").type(JsonFieldType.STRING).description("사용자 인가코드")
                                ),
                                responseFields(
                                        fieldWithPath("accessToken").type(JsonFieldType.STRING).description("로그인 엑세스 토큰")
                                )
                        )
                );
    }
}
