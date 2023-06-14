package com.tacos.demo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class DeliveriesController {

    private static final Logger log = LogManager.getLogger(DeliveriesController.class);

    @GetMapping("/deliveries")

    public Boolean deliveries(HttpRequest request) {
        log.info("Received HTTP GET request. Path: {}, Remote Address: {}", request.getURI().getPath());
        if(ThreadLocalRandom.current().nextDouble() < 0.8)
        {return true;}
        else
        {
            for (int i = 0; i < 10; i++) {
                fibonacci(i);
            }
            return false;
        }
    }

    public static long fibonacci(long n) {
        if (n == 0 || n == 1) {
          return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
      }
}