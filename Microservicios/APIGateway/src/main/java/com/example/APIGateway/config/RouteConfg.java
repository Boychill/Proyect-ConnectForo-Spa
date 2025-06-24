package com.example.APIGateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfg {
    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

            .route("auth-service", r -> r.path("/auth/**")
                .uri("lb://auth-service"))

            .route("category-service", r -> r.path("/categorias/**")
                .uri("lb://category-service"))

            .route("forum-service", r -> r.path("/foros/**")
                .uri("lb://forum-service"))

            .route("publication-service", r -> r.path("/publicaciones/**")
                .uri("lb://publication-service"))

            .route("comment-service", r -> r.path("/comentarios/**")
                .uri("lb://comment-service"))

            .route("reputation-service", r -> r.path("/likes/**")
                .uri("lb://reputation-service"))

            .build();
    }
}
