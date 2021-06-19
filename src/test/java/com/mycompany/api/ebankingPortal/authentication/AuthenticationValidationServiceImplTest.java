package com.mycompany.api.ebankingPortal.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.api.ebankingPortal.exception.ApiError;
import com.mycompany.api.ebankingPortal.exception.BadRequestException;
import com.mycompany.api.ebankingPortal.exception.InvalidCustomerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationValidationServiceImplTest {

    @Autowired
    private AuthenticationValidationService service;

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${services.url.authenticate-service}")
    private String authenticateUrl;


    private MockRestServiceServer mockServer;
    private ObjectMapper mapper;

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        mapper = new ObjectMapper();
    }

    @Test
    public void validateCustomer_throws_BadRequestExceptionIfNoAuthHeadersProvided() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        Assertions.assertThrows(BadRequestException.class, () -> {
            service.validateAndGetCustomer(headers, null);
        });
    }

    @Test
    public void validateCustomer_throws_InvalidCustomerException_If_AuthHeaders_NotMatching_CustomerId() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "mock_customer_id");
        Assertions.assertThrows(InvalidCustomerException.class, () -> {
            service.validateAndGetCustomer(headers, "invalid_customer_id");
        });
    }

    @Test
    public void validateCustomer_throws_InvalidCustomerException_If_AuthServiceInvalidate_CustomerId() throws Exception {

        // Mock Data
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, "Invalid Request", "No authentication headers");
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(authenticateUrl)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(error))
                );

        // Set Params
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "mock_customer_id");

        // Actual Call
        Assertions.assertThrows(InvalidCustomerException.class, () -> {
            service.validateAndGetCustomer(headers, "mock_customer_id");
        });
    }

    @Test
    public void validateCustomer_AuthServiceValidates_token() throws Exception {

        // Mock Data
        CustomerDetails mockCustomerDetails = new CustomerDetails("mock_customer_id");
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(authenticateUrl)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(mockCustomerDetails))
                );

        // Set Params
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "mock_customer_id");

        // Actual Call
        CustomerDetails actualCustomerDetails = service.validateAndGetCustomer(headers, "mock_customer_id");

        // Assertions
        Assertions.assertEquals(mockCustomerDetails, actualCustomerDetails);
    }

}
