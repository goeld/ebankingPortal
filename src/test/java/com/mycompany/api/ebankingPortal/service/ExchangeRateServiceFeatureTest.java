package com.mycompany.api.ebankingPortal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.api.ebankingPortal.configuration.CucumberSpringContextConfiguration;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.platform.engine.Cucumber;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@Cucumber
public class ExchangeRateServiceFeatureTest extends CucumberSpringContextConfiguration {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${services.url.exchange-rate}")
    private String exchangeRateUrl;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();
    private Map<String, Double> exchangeRateMap;
    Map<String, Double> actualExchangeRates;

    private Map<String, Double> mock_exchange_rates() {
        Map<String, Double> rates = new HashMap<>();
        rates.put("SGD/MYR", 3.49099d);
        rates.put("SGD/USD", 0.7788d);
        rates.put("SGD/INR", 55.20778d);
        rates.put("USD/SGD", 1.433d);
        return rates;
    }

    @Given("Provider is up")
    public void provider_is_up() throws JsonProcessingException, URISyntaxException {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        exchangeRateMap = mock_exchange_rates();
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(exchangeRateUrl)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(exchangeRateMap))
                );
    }

    @When("Exchange rate is enquired")
    public void exchange_service_is_called() {
        actualExchangeRates = exchangeRateService.getAllExhangeRates();
    }

    @Then("exchange rates are provided for all the pairs")
    public void rate_are_provided() {
        long diff = actualExchangeRates.entrySet().stream().filter(e ->
                !exchangeRateMap.get(e.getKey()).equals(e.getValue())
        ).count();
        Assert.assertEquals(0, diff);
    }
}
