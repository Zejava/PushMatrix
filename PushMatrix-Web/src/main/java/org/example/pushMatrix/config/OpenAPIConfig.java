package org.example.pushMatrix.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  @program: pushMatrix
 *  * @description: swagger
 * @Author 泽
 * @Date 2024/8/11 15:39
 */
@Configuration
public class OpenAPIConfig  {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("pushMatrix")
                        .description("SpringBoot3 集成 Swagger3接口文档")
                        .version("v1"))
                .externalDocs(new ExternalDocumentation()
                        .description("pushMatrixAPI文档")
                        .url("/"));
    }
}
