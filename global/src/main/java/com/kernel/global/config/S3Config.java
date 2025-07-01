package com.kernel.global.config;

import com.kernel.global.common.properties.AwsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
@EnableConfigurationProperties(AwsProperties.class)
public class S3Config {

    private final AwsProperties awsProperties;

    public S3Config(AwsProperties awsProperties) {
        this.awsProperties = awsProperties;
    }

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
                awsProperties.getCredentials().getAccessKey(),
                awsProperties.getCredentials().getSecretKey()
        );


        return S3Client.builder()
                .region(Region.of(awsProperties.getRegion().getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
                awsProperties.getCredentials().getAccessKey(),
                awsProperties.getCredentials().getSecretKey()
        );

        return S3Presigner.builder()
                .region(Region.of(awsProperties.getRegion().getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }

    @Bean
    public String s3BucketName() {
        return awsProperties.getS3().getBucket();
    }
}

