/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.library_management_system.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author 123
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI libraryOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Library Management System API")
                .description("API documentation for the Library Management System")
                .version("1.0.0"));
    }
}
