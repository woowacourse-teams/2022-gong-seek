package com.woowacourse.gongseek.member.application;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IdentifierEncryptor implements Encryptor {

    private final String encryptionAlgorithm;

    public IdentifierEncryptor(@Value("${security.encryption.algorithm}") String encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
    }

    public String encrypt(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance(encryptionAlgorithm);
            md.update(text.getBytes());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException();
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
