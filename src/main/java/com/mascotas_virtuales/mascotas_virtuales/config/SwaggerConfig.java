package com.mascotas_virtuales.mascotas_virtuales.config;


import org.hibernate.annotations.Comment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Comment("Esta clase configura Swagger para la documentacion de la API")
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(springfox.documentation.spi.DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mascotas_virtuales.mascotas_virtuales.controllers"))
                .paths(PathSelectors.any())
                .build();
    }

}
