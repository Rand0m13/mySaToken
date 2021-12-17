package com.rangamer.mysatoken.config;

/**
 * @author jesse
 * @date 2021年12月16日 15:23
 */

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * 访问地址 http://127.0.0.1:8081/doc.html
 */
@Configuration
@EnableKnife4j
@EnableSwagger2WebMvc
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return  new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                //方式一: 配置扫描 所有想在swagger界面的统一管理接口。都必须在此包下
                .apis(RequestHandlerSelectors.basePackage("com.rangamer.mysatoken.controller"))
                //方式二: 只有当方法上有  @ApiOperation 注解时才能生成对应的接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Knife4j APIs")
                .description("swagger-bootstrap-ui")
                .termsOfServiceUrl("http://localhost:8081/")
                .version("1.0")
                .build();
    }

}


