package dev.matheuscruz;

import io.quarkus.logging.Log;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import java.net.InetAddress;

@ApplicationScoped
public class JqWarmup {

    void onStart(@Observes StartupEvent event) {
        try {
            InetAddress.getLocalHost();
        } catch (Exception e) {
            Log.warn("Local hostname resolution failed during warmup", e);
        }
    }
}
