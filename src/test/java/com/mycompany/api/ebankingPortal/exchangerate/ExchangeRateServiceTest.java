package com.mycompany.api.ebankingPortal.exchangerate;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.api.ebankingPortal.exception.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest
@AutoConfigureMockMvc
public class ExchangeRateServiceTest {

    @Value("${services.url.exchange-rate}")
    private String exchangeRateUrl;

    @Autowired
    private ExchangeRateService service;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void getAllExchangeRates_success() throws Exception {

        //Mock
        mockServer = MockRestServiceServer.createServer(restTemplate);
        // Mock Data
        Map<String, Double> mockExchangeRates = mock_exchange_rates();
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(exchangeRateUrl)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(mockExchangeRates))
                );

        // Actual call
        Map<String, Double> actualExchangeRates = service.getAllExhangeRates();

        // Assertions
        mockServer.verify();
        long diff = actualExchangeRates.entrySet().stream().filter(e ->
                !mockExchangeRates.get(e.getKey()).equals(e.getValue())
        ).count();
        Assertions.assertEquals(0, diff);
    }

    @Test
    public void getAllExchangeRates_Missing_Auth_header() throws Exception {

        //Mock
        mockServer = MockRestServiceServer.createServer(restTemplate);
        // Mock Data
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Invalid Request", "No authentication headers");
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(exchangeRateUrl)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(error))
                );

        // Actual call
        Assertions.assertThrows(BadRequestException.class, () -> {
            service.getAllExhangeRates();
        });
    }

    @Test
    public void getAllExchangeRates_Unauthorised_User_throw_forbiddent_exception() throws Exception {

        //Mock
        mockServer = MockRestServiceServer.createServer(restTemplate);
        // Mock Data
        ApiError error = new ApiError(HttpStatus.FORBIDDEN, "Invalid Request", "User is not authenticated");
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(exchangeRateUrl)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.FORBIDDEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(error))
                );

        // Actual call
        Assertions.assertThrows(ForbiddenException.class, () -> {
            service.getAllExhangeRates();
        });
    }


    @Test
    public void getAllExchangeRates_server_error() throws Exception {

        //Mock
        mockServer = MockRestServiceServer.createServer(restTemplate);
        // Mock Data
        ApiError error = new ApiError(HttpStatus.BAD_GATEWAY, "Bad Gateway", "Server Error");
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(exchangeRateUrl)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.BAD_GATEWAY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(error))
                );

        // Actual call
        Assertions.assertThrows(ServerException.class, () -> {
            service.getAllExhangeRates();
        });
    }

    @Test
    public void getAllExchangeRates_client_random_error() throws Exception {

        //Mock
        mockServer = MockRestServiceServer.createServer(restTemplate);
        // Mock Data
        ApiError error = new ApiError(HttpStatus.UNPROCESSABLE_ENTITY, "Client Error in processing", "Client Error");
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(exchangeRateUrl)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.UNPROCESSABLE_ENTITY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(error))
                );

        // Actual call
        Assertions.assertThrows(ServiceProviderException.class, () -> {
            service.getAllExhangeRates();
        });
    }

    private Map<String, Double> mock_exchange_rates() {
        Map<String, Double> rates = new HashMap<>();
        rates.put("SGD/MYR", 3.49099d);
        rates.put("SGD/USD", 0.7788d);
        rates.put("SGD/INR", 55.20778d);
        rates.put("USD/SGD", 1.433d);
        return rates;
    }

}