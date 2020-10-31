package com.mobile.search.config;

import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * This class configure swagger for application.
 * 
 * @author Suresh Thakare
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("com.mobile.search.controller"))
        .paths(PathSelectors.any()).build().apiInfo(apiInfo())
        .securitySchemes(Collections.singletonList(apiKey()))
        .securityContexts(Collections.singletonList(securityContext()));
  }

  private ApiKey apiKey() {
    return new ApiKey("Authorization", "Authorization", "header");
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder().title("Mobile Search API").description(
        "This API search the mobile details based on the value provided for mobile detail parameters")
        .build();
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder().securityReferences(defaultAuth())
        .forPaths(PathSelectors.regex("/.*")).build();
  }

  private List<SecurityReference> defaultAuth() {
    final AuthorizationScope authorizationScope =
        new AuthorizationScope("global", "accessEverything");
    final AuthorizationScope[] authorizationScopes = new AuthorizationScope[] {authorizationScope};
    return Collections.singletonList(new SecurityReference("Authorization", authorizationScopes));
  }
}
