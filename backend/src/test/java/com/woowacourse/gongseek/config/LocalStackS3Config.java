package com.woowacourse.gongseek.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@TestConfiguration
public class LocalStackS3Config {
    private static final LocalStackContainer localStackContainer = new LocalStackContainer(
            DockerImageName.parse("localstack/localstack")).withServices(LocalStackContainer.Service.S3);

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .endpointOverride(localStackContainer.getEndpointOverride(Service.S3))
                .credentialsProvider(InstanceProfileCredentialsProvider.builder().build())
                .region(Region.of(localStackContainer.getRegion()))
                .build();
    }
}
