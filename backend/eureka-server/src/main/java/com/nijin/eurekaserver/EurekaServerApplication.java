package com.nijin.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Main application class for the Eureka Server.
 * This class is annotated with {@link SpringBootApplication} to enable Spring Boot
 * auto-configuration. It also includes annotations such as {@link EnableEurekaServer}
 * and {@link EnableWebMvc} to enable Eureka Server functionality and customize Spring MVC
 * configuration, respectively.
 *
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 * @see org.springframework.cloud.netflix.eureka.server.EnableEurekaServer
 * @see org.springframework.web.servlet.config.annotation.EnableWebMvc
 **/

@SpringBootApplication
@EnableEurekaServer
@EnableWebMvc
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}