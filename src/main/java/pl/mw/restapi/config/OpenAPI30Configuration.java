package pl.mw.restapi.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class OpenAPI30Configuration {
    @Bean
    public OpenAPI springShopOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()

                //.addSecurityItem(new SecurityRequirement().addList("Authorization"))
                .components(new Components()
                        .addSecuritySchemes("Authorization", new SecurityScheme()
                                .in(SecurityScheme.In.HEADER)
                                .type(SecurityScheme.Type.HTTP)
                                //.type(SecurityScheme.Type.APIKEY)
                                //.name("Authorization")
                                .name("Bearer Authentication")
                                .scheme("bearer")
                                .bearerFormat("JWT"))
                )
                //.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))

                .info(new Info()
                        .title("Qalku API")
                        .description("Przykładowa aplikacja REST API")
                        .version("v0.0.1")
                        .contact(new Contact().email("marcin.seiho@gmail.com").url("https://qalku.pl").name("Marcin Wrona"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("QalkuAPI Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs")
                );
    }
/*
    @Bean
    public GroupedOpenApi publicApi() {

        return GroupedOpenApi.builder()
                .group("qalku-public")
                .pathsToMatch("/**")
                .addOpenApiCustomizer(publicApiCustomizer())
                .build()
                ;
    }

    @Bean
    public OpenApiCustomizer publicApiCustomizer() {

        final String securitySchemeName = "bearerAuth";
        return openApi -> openApi.addSecurityItem(new SecurityRequirement().addList("Authorization"))
                .components(new Components()
                        .addSecuritySchemes("Authorization", new SecurityScheme()
                                .in(SecurityScheme.In.HEADER)
                                .type(SecurityScheme.Type.HTTP)
                                .name("Bearer Authentication")
                                .scheme("bearer")
                                .bearerFormat("JWT"))
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList(securitySchemeName))
                .info(new Info().title("Public REST API"));

    }

    @Bean
    public OpenApiCustomizer internalApiCustomizer() {
        final String securitySchemeName = "bearerAuth";
        return openApi -> openApi
                .addSecurityItem(new SecurityRequirement().addList("apiKey"))
                .components(new Components()
                        .addSecuritySchemes("apiKey", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .name("Bearer Authentication")
                                .in(SecurityScheme.In.HEADER)
                                .name("apiKey")))
                .info(new Info().title("Internal REST API"));
    }
*/
}
