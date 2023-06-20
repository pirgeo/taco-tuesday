package com.tacos.demo;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import jakarta.servlet.http.HttpServletRequest;

@RestController

public class OrderController {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(OrderController.class);
    @RequestMapping("/{tacos}")

    public String handleGetRequest(HttpServletRequest request, @PathVariable String tacos) throws IOException {
        Counter orders = Metrics.counter("orders");
        log.info("Received HTTP GET request. Path: {}, Remote Address: {}", request.getRequestURL(), request.getRemoteAddr());
        int tacosInt = (tacos != null) ? Integer.parseInt(tacos) : 1;
        getOrders(tacosInt);
        orders.increment(tacosInt);
        return "order received";
    }

    public String getOrders(int tacos) throws IOException {     
        Counter successfulDeliveries = Metrics.counter("deliveries","success","true");
        Counter unsuccessfulDeliveries = Metrics.counter("deliveries","success","false");
        URL url = new URL ("http://deliveries:8081/");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        String response = new String(con.getInputStream().readAllBytes(),StandardCharsets.UTF_8);
        if(response.equals("true")){
            successfulDeliveries.increment(tacos);
            String returnMessage = "successfully delivered " + tacos + " tacos";
            log.info(returnMessage);
            return returnMessage;
        }else{
            unsuccessfulDeliveries.increment(tacos);
            String returnMessage = "failed to deliver" + tacos + " tacos";
            log.info(returnMessage);
            return returnMessage;
        }
    }
}
