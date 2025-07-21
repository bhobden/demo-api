package com.eaglebank.api.metrics;

import java.util.Arrays;
import java.util.stream.Stream;

import com.eaglebank.api.validation.exception.HttpRuntimeException;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;

/**
 * MetricScope is a utility class for timing and error metrics in EagleBank.
 * It uses Micrometer to record operation duration and error counters, 
 * automatically tagging metrics with status codes derived from exceptions.
 * 
 * Usage:
 * <pre>
 * try (MetricScope scope = MetricScopeFactory.of("operation.name")) {
 *     // business logic
 * } catch (Exception e) {
 *     scope.markFailure(e);
 *     throw e;
 * }
 * </pre>
 */
public class MetricScope implements AutoCloseable {

    private final Timer.Sample sample;
    private final Timer timer;
    private final MeterRegistry registry;
    private final String[] tags;
    private Throwable throwable;

    /**
     * Constructs a MetricScope for timing and error metrics.
     *
     * @param registry   The MeterRegistry to record metrics.
     * @param timerName  The name of the timer metric.
     * @param tags       Additional tags for the metric.
     */
    public MetricScope(MeterRegistry registry, String timerName, String... tags) {
        this.registry = registry;
        this.tags = tags;
        this.sample = Timer.start(registry);
        this.timer = registry.timer(timerName);
    }

    /**
     * Records the timer and increments error counter if an exception occurred.
     * Automatically tags the error with status code derived from the exception.
     */
    @Override
    public void close() {
        sample.stop(timer);
        if (throwable != null) {
            registry.counter("eaglebank.error", getTags()).increment();
        }
    }

    /**
     * Returns all tags for the metric, including status code if an exception occurred.
     *
     * @return String[] of tags for the metric.
     */
    protected String[] getTags() {
        // TODO: validate tags are not null and are valid pairs
        return Stream.concat(
                Arrays.stream(tags),
                Stream.of("status", classifyStatus(throwable))).toArray(String[]::new);
    }

    /**
     * Attaches a throwable to this scope for error tagging on close.
     *
     * @param t The throwable to attach.
     */
    public void markFailure(Throwable t) {
        this.throwable = t;
    }

    /**
     * Derives status code label from exception type.
     *
     * @param t The throwable to classify.
     * @return String status code (e.g., "400", "403", "500").
     */
    private String classifyStatus(Throwable t) {
        if (t instanceof HttpRuntimeException)
            return Integer.toString(((HttpRuntimeException) t).getStatusCode());
        if (t instanceof IllegalArgumentException)
            return "400";
        if (t instanceof SecurityException)
            return "403";
        return "500"; // default fallback
    }
}
