package org.patientlog.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Actuator", description = "Spring Boot actuator endpoints for Swagger")
public class ActuatorController {

    private final HealthEndpoint healthEndpoint;
    private final MetricsEndpoint metricsEndpoint;

    public ActuatorController(HealthEndpoint healthEndpoint, MetricsEndpoint metricsEndpoint) {
        this.healthEndpoint = healthEndpoint;
        this.metricsEndpoint = metricsEndpoint;
    }

    @GetMapping("/swagger/actuator/health")
    @Operation(summary = "Get health info")
    public Object health() {
        return healthEndpoint.health();
    }

    @GetMapping("/swagger/actuator/metrics")
    @Operation(summary = "Get metrics info")
    public Object metrics() {
        return metricsEndpoint.listNames();
    }
}
