package com.kernel;

import com.kernel.global.common.properties.AwsProperties;
import com.kernel.global.common.properties.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication(scanBasePackages = "com.kernel")
@EnableJpaRepositories(basePackages = "com.kernel")
@EntityScan(basePackages = "com.kernel")
@EnableConfigurationProperties({
        JwtProperties.class,
        AwsProperties.class
})
@EnableRetry
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
