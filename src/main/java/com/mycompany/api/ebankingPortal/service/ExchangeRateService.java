package com.mycompany.api.ebankingPortal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class ExchangeRateService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${services.url.exchange-rate}")
    private String exchangeRateUrl;

    public Map<String, Double> getAllExhangeRates() {

        ResponseEntity<Map<String, Double>> responseEntity =
                restTemplate.exchange(exchangeRateUrl, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Double>>() {
                });
        return responseEntity.getBody();
    }


}
