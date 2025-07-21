package com.eaglebank.api.metrics;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestMetricsInterceptor implements HandlerInterceptor {

    private final ThreadLocal<MetricScope> currentScope = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String endpoint = request.getRequestURI();
        String timerName = "eaglebank.request.duration";

        MetricScope scope = MetricScopeFactory.of( timerName, endpoint);
        currentScope.set(scope);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        MetricScope scope = currentScope.get();
        if (scope != null) {
            if (ex != null) {
                scope.markFailure(ex);
            }
            scope.close(); // stop timer + tag error
            currentScope.remove(); // clean up thread-local
        }
    }
}
