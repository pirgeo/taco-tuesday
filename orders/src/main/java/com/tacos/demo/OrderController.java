package com.tacos.demo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;

@RestController

public class OrderController {

    @GetMapping("/orders")

    public String getOrders() throws IOException {
        Counter orders = Metrics.counter("orders");
        Counter successfulDeliveries = Metrics.counter("deliveries","success","true");
        Counter unsuccessfulDeliveries = Metrics.counter("deliveries","success","false");
        orders.increment();
        URL url = new URL ("http://deliveries:8081/deliveries");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        String response = new String(con.getInputStream().readAllBytes(),StandardCharsets.UTF_8);
        if(response.equals("true")){
            successfulDeliveries.increment();
            System.out.println("delivery successful");
            return "delivery successful";
        }else{
            unsuccessfulDeliveries.increment();
            System.out.println("delivery unsuccessful");
            return "delivery unsuccessful";
        }
    }
}
