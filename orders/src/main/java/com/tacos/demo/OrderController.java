package com.tacos.demo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;

@RestController

public class OrderController {

    private static final Logger log = LogManager.getLogger(OrderController.class);


    @GetMapping("/orders")

    public String orders(HttpRequest request) throws IOException {
        log.info("Received HTTP GET request. Path: {}, Remote Address: {}", request.getURI().getPath());
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
            log.info("delivery successful");
            return "delivery successful";
        }else{
            unsuccessfulDeliveries.increment();
            log.info("delivery unsuccessful");
            return "delivery unsuccessful";
        }
    }
}
