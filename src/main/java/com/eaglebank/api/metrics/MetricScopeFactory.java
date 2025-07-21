package com.eaglebank.api.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MetricScopeFactory {

    private static MeterRegistry globalRegistry;

    public MetricScopeFactory(MeterRegistry registry) {
        MetricScopeFactory.globalRegistry = registry;
    }

    /**
     * Static helper to create a scoped metric without manually injecting the registry.
     */
    public static MetricScope of(String timerName, String endPoint) {
        return new MetricScope(globalRegistry, timerName, "enpPoint", endPoint);
    }

    /**
     * Static helper to create a scoped metric without manually injecting the registry.
     */
    public static MetricScope of(String timerName) {
        return new MetricScope(globalRegistry, timerName);
    }
}
