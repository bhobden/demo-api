package com.eaglebank.api.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

/**
 * Factory for creating {@link MetricScope} instances for timing and metrics.
 * <p>
 * Provides static helpers to create scoped metrics for code blocks, using a global
 * {@link MeterRegistry} injected by Spring. This allows for easy timing of operations
 * without manual registry management.
 * </p>
 */
@Component
public class MetricScopeFactory {

    private static MeterRegistry globalRegistry;

    /**
     * Initializes the global MeterRegistry for use in static helpers.
     *
     * @param registry the MeterRegistry to use globally
     */
    public MetricScopeFactory(MeterRegistry registry) {
        MetricScopeFactory.globalRegistry = registry;
    }

    /**
     * Creates a {@link MetricScope} for the given timer name and endpoint.
     *
     * @param timerName the name of the timer metric
     * @param endPoint  the endpoint or label for the metric
     * @return a new MetricScope instance for timing
     */
    public static MetricScope of(String timerName, String endPoint) {
        return new MetricScope(globalRegistry, timerName, "enpPoint", endPoint);
    }

    /**
     * Creates a {@link MetricScope} for the given timer name.
     *
     * @param timerName the name of the timer metric
     * @return a new MetricScope instance for timing
     */
    public static MetricScope of(String timerName) {
        return new MetricScope(globalRegistry, timerName);
    }
}
