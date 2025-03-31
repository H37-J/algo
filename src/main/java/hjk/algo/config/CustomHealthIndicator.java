package hjk.algo.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        return Health.up().withDetail("custom", "active").build();
    }
}