package com.meta.gateway.routes;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.web.servlet.function.RequestPredicates.GET;

@Configuration
public class Routes {
    @Value("${office.service.url}")
    private String officeServiceUrl;
    @Value("${user.service.url}")
    private String userServiceUrl;
    @Value("${document.service.url}")
    private String documentServiceUrl;
    @Value("${activity.tracker.url}")
    private String activityTrackerServiceUrl;
    @Value("${project.manager.url}")
    private String projectManagerServiceUrl;

    //fallback URI
    private static final URI FALLBACK_URI = URI.create("forward:/fallbackRoute");

    // API docs path
    private static final String API_DOCS_PATH = "/v3/api-docs";

    // Office Service Routes

    @Bean
    public RouterFunction<ServerResponse> officeServiceRoute() {
        return route("office_service")
                .route(RequestPredicates.path("/os/**"), HandlerFunctions.http(officeServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("officeServiceCircuitBreaker", FALLBACK_URI))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> officeServiceSwaggerRoute() {
        return route("office_service_swagger")
                .route(GET("/aggregate/office-service/v3/api-docs"), HandlerFunctions.http(officeServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("officeServiceSwaggerCircuitBreaker", FALLBACK_URI))
                .filter(setPath(API_DOCS_PATH))
                .build();
    }

    // User Service Routes

    @Bean
    public RouterFunction<ServerResponse> userServiceRoute() {
        return route("user_service")
                .route(RequestPredicates.path("/us/**"), HandlerFunctions.http(userServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("userServiceCircuitBreaker", FALLBACK_URI))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userServiceSwaggerRoute() {
        return route("user_service_swagger")
                .route(GET("/aggregate/user-service/v3/api-docs"), HandlerFunctions.http(userServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("userServiceSwaggerCircuitBreaker", FALLBACK_URI))
                .filter(setPath(API_DOCS_PATH))
                .build();
    }

    // Document Service Routes

    @Bean
    public RouterFunction<ServerResponse> documentServiceRoute() {
        return route("document_service")
                .route(RequestPredicates.path("/ds/**"), HandlerFunctions.http(documentServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("documentServiceCircuitBreaker", FALLBACK_URI))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> documentServiceSwaggerRoute() {
        return route("document_service_swagger")
                .route(GET("/aggregate/document-service/v3/api-docs"), HandlerFunctions.http(documentServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("documentServiceSwaggerCircuitBreaker", FALLBACK_URI))
                .filter(setPath(API_DOCS_PATH))
                .build();
    }


    // Activity Tracker Service Routes

    @Bean
    public RouterFunction<ServerResponse> activityTrackerServiceRoute() {
        return route("activity_tracker_service")
                .route(RequestPredicates.path("/ac/**"), HandlerFunctions.http(activityTrackerServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("activityTrackerServiceCircuitBreaker", FALLBACK_URI))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> activityTrackerServiceSwaggerRoute() {
        return route("activity_tracker_service_swagger")
                .route(GET("/aggregate/activity-tracker-service/v3/api-docs"), HandlerFunctions.http(activityTrackerServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("activityTrackerServiceSwaggerCircuitBreaker", FALLBACK_URI))
                .filter(setPath(API_DOCS_PATH))
                .build();
    }


    // Project Manager Service Routes

    @Bean
    public RouterFunction<ServerResponse> projectManagerServiceRoute() {
        return route("project_manager_service")
                .route(RequestPredicates.path("/pm/**"), HandlerFunctions.http(projectManagerServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("projectManagerServiceCircuitBreaker", FALLBACK_URI))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> projectManagerServiceSwaggerRoute() {
        return route("project_manager_service_swagger")
                .route(GET("/aggregate/project-manager-service/v3/api-docs"), HandlerFunctions.http(projectManagerServiceUrl))
                .filter(CircuitBreakerFilterFunctions.circuitBreaker("projectManagerServiceSwaggerCircuitBreaker", FALLBACK_URI))
                .filter(setPath(API_DOCS_PATH))
                .build();
    }





    @Bean
    public RouterFunction<ServerResponse> fallbackRoute(){
        return route("fallbackRoute")
                .GET("/fallbackRoute", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).body("Service is Unavailable for now, please try again later."))
                .build();
    }


}