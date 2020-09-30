package com.wyona.multitenantservice.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.context.annotation.Bean;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.builders.RequestHandlerSelectors;
import static springfox.documentation.builders.PathSelectors.regex;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableSwagger2
@SpringBootApplication
@ComponentScan("com.wyona.multitenantservice.webapp")
/**
 *
 */
public class Server extends SpringBootServletInitializer {

    /**
     *
     */
    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

    /**
     *
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Server.class);
    }

    @Bean
    public Docket swaggerApiV1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("API V1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wyona.multitenantservice.webapp"))
                .paths(regex("/.*/v1.*"))
                .build()
                .apiInfo(new ApiInfoBuilder().version("1.0").title("API").description("Documentation API V1").build());
    }

    @Bean
    public Docket swaggerApiV2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("API V2")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wyona.multitenantservice.webapp"))
                .paths(regex("/.*/v2.*"))
                .build()
                .apiInfo(new ApiInfoBuilder().version("2.0").title("API").description("Documentation API V2").build());
    }
}
