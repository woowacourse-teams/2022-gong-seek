package com.woowacourse.gongseek.member.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest
class IdentifierEncryptorTest {

    @Autowired
    private Encryptor encryptor;

    @Test
    void 같은_값을_암호화하면_같은_값이_나온다() {
        Long id = 1L;

        String cipherText = encryptor.encrypt(String.valueOf(id));

        assertThat(cipherText).isEqualTo(encryptor.encrypt(String.valueOf(id)));
    }
}
