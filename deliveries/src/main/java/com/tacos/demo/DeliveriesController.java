package com.tacos.demo;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;

@RestController

public class DeliveriesController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DeliveriesController.class);

    @GetMapping("/")

    public Boolean deliveries(HttpServletRequest request){
        log.info("Received HTTP GET request. Path: {}, Remote Address: {}", request.getRequestURL(), request.getRemoteAddr());        
        if(ThreadLocalRandom.current().nextDouble() < 0.8)
        {return true;}
        else
        {
            for (int i = 0; i < 40; i++) {
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