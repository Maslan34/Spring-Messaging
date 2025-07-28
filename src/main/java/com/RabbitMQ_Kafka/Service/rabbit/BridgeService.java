package com.RabbitMQ_Kafka.Service.rabbit;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("rabbit")
@Service
public class BridgeService {

    private final CircuitBreaker circuitBreaker;
    private final AlternativeTransportService fallbackService;

    public BridgeService(CircuitBreakerRegistry registry, AlternativeTransportService fallbackService) {
        this.circuitBreaker = registry.circuitBreaker("bridgeControlCB");
        this.fallbackService = fallbackService;
    }

    public String evaluateWindSpeed(double speed) {
        return circuitBreaker.executeSupplier(() -> {
            if (speed > 25) {
                throw new RuntimeException("❌ Extreme wind!");
            }
            return "✅ Wind speed at the desired level. Wind: " + speed + " m/s";
        });
    }

    public CircuitBreaker.State getCircuitBreakerState() {
        return circuitBreaker.getState();
    }

    public String useAlternativeTransport(double speed, Throwable t) {
        return fallbackService.fallback(speed, t);
    }
}
