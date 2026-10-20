package br.com.yago.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.*;

@Configuration
public class OpenAPIConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hello Swagger OpenAPI")
                        .version("v1")
                        .description("This is a Swagger Documentation")
                        .termsOfService("https://yagowalter.com.br/")
                        .license(new License()
                                .name("Some License")
                                .url("https://github.com/yagowalter")));
    }
}
