package com.alan.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author cxxwl96@sina.com
 * @date 2020/12/9
 */

@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "project.swagger-enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("默认接口")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.alan.devtools"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("alan后台管理系统数据接口(knife4j-ui版)")
                .description("Swagger是一个规范和完整的框架，用于生成、描述、调用和可视化RESTful风格的Web服务。")
                .termsOfServiceUrl("http://www.cxxwl96.cn")
                .contact(new Contact("cxxwl96@sina.com", "", ""))
                .version("v1.0")
                .build();
    }
}
