package com.xiao.learn_rabbitmq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author aloneMan
 * @projectName plain-redis
 * @createTime 2022-08-22 21:07:01
 * @description
 */
@Component
public class SwaggerConfig {



    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("rabbitmq学习")
                .contact(new Contact("rabbit-xiao", "", "test@x.com"))
                .version("1.0")
                .description("rabbitmq学习")
                .build();
    }
}
