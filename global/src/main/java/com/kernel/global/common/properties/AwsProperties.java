package com.kernel.global.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "cloud.aws")
@Getter
@Setter
public class AwsProperties {
    private Region region;
    private S3 s3;
    private Credentials credentials;

    @Getter @Setter
    public static class S3 {
        private String bucket;
    }

    @Getter @Setter
    public static class Credentials {
        private String accessKey;
        private String secretKey;
    }

    @Getter @Setter
    public static class Region {
        private String region;
    }
}

