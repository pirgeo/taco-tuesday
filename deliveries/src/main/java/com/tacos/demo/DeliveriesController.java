package com.tacos.demo;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class DeliveriesController {

    @GetMapping("/deliveries")
    public Boolean getDeliveries() {
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