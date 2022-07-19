package com.woowacourse.gongseek.acceptance;

import static com.woowacourse.gongseek.acceptance.support.AuthFixtures.로그인을_한다;
import static com.woowacourse.gongseek.auth.support.GithubClientFixtures.주디;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.woowacourse.gongseek.auth.presentation.dto.OAuthLoginUrlResponse;
import com.woowacourse.gongseek.auth.presentation.dto.TokenResponse;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
public class AuthAcceptanceTest extends AcceptanceTest {

    @Test
    void 유저가_깃허브로_로그인을_할_수_있다() {
        // given
        OAuthLoginUrlResponse urlResponse = 로그인_URL을_얻는다();

        //when
        TokenResponse tokenResponse = 로그인을_한다(주디);

        //then
        assertAll(
                () -> assertThat(urlResponse.getUrl()).isNotNull(),
                () -> assertThat(tokenResponse.getAccessToken()).isNotNull()
        );
    }
}
