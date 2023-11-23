package com.nijin.dbOps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Main application class for the Database Operations service.
 * This class is annotated with {@link SpringBootApplication} to enable Spring Boot
 * auto-configuration. It also includes additional annotations such as {@link EnableEurekaClient},
 * {@link EnableFeignClients}, and {@link EnableWebMvc} for enabling Eureka client registration,
 * Feign client support, and customizing Spring MVC configuration, respectively.
 *
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 * @see org.springframework.cloud.netflix.eureka.EnableEurekaClient
 * @see org.springframework.cloud.openfeign.EnableFeignClients
 * @see org.springframework.web.servlet.config.annotation.EnableWebMvc
 **/

@SpringBootApplication(
        scanBasePackages = {
                "com.nijin.dbOps",
                "com.nijin.amqp"
        }
)
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.nijin.clients.logInjection"
)
@EnableWebMvc
public class DbApplication {
    public static void main(String[] args) {
        SpringApplication.run(DbApplication.class, args);
    }

}
