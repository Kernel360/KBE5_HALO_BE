package com.kernel.app;

import com.kernel.app.jwt.JwtProperties;
import com.kernel.common.config.AwsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.kernel")
@EnableJpaRepositories(basePackages = "com.kernel")
@EntityScan(basePackages = "com.kernel")
@ComponentScan(basePackages = "com.kernel")
@EnableConfigurationProperties({
        JwtProperties.class,
        AwsProperties.class
})
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

}
