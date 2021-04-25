package com.example.armeria_prometheus;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linecorp.armeria.client.WebClient;
import com.linecorp.armeria.common.AggregatedHttpResponse;

public class HttpClientApplication {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientApplication.class);

    public static void main(String[] args) {
        WebClient client = WebClient.of("http://localhost:8083/");
        for (int i = 0; i < 100; i++) {
            AggregatedHttpResponse res = client.get("/hello/armeria").aggregate().join();
            logger.info(res.content(StandardCharsets.UTF_8));
        }
    }
}
