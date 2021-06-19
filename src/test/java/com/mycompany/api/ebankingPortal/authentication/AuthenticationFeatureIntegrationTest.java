package com.mycompany.api.ebankingPortal.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.api.ebankingPortal.customer.AccountException;
import com.mycompany.api.ebankingPortal.customerAccountTransaction.*;
import com.mycompany.api.ebankingPortal.exception.*;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.platform.engine.Cucumber;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;


@Cucumber
@RunWith(SpringRunner.class)
public class AuthenticationFeatureIntegrationTest {

    private String exceptionMessage;

    @InjectMocks
    @Autowired
    private CustomerAccountTransactionController controller;

    @Mock
    private CustomerAccountTransactionService customerAccountTransactionService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${services.url.authenticate-service}")
    private String authenticateUrl;

    private static final String customerTransactionUrl = "/ebanking/customer/transactions";

    private HttpHeaders headers;
    private String customerId;

    @When("A customer is trying to access transactions without proper authentication")
    public void when_customer_login_without_auth_header() {
        // No Authentication headers are set
        headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
    }

    @When("A customer has invalid authentication header")
    public void when_customer_has_invalid_auth_token() {
        // No Authentication headers are set
        headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add("authorization", "mock_customer_id");
    }

    @When("A customer has valid authentication header")
    public void when_customer_has_auth_header() {
        // No Authentication headers are set
        headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add("authorization", "mock_customer_id");
    }

    @When("Authentication Header is not set")
    public void when_authentication_header_is_missing() {
        // No Authentication headers are set
        headers = new HttpHeaders();
    }

    @When("customerId is invalid")
    public void when_customer_id_is_invalid() {
        this.customerId = "invalid_customerId";
    }

    @When("customerId is valid")
    public void when_customer_id_is_valid() {
        this.customerId = "mock_customer_id";
    }

    @Then("System should fail the request")
    public void system_fails_the_request() throws URISyntaxException, JsonProcessingException {

        // Mock - Authentication Rest API Call
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, "Invalid Request", "No authentication headers");
        ObjectMapper mapper = new ObjectMapper();
        MockRestServiceServer mockServer;
        mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(authenticateUrl)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(error))
                );

        // Actual Call
        exceptionMessage = Assertions.assertThrows(Exception.class, () -> {
            controller.getCustomerTransactions(new CustomerTransactionRequest(), headers);
        }).getMessage();
    }

    @Then("'Invalid Customer' message is shown")
    public void verify_invalid_customer_message() {
        Assertions.assertEquals("Invalid Customer", this.exceptionMessage);
    }

    @Then("'Invalid authentication details' message is shown")
    public void verify_bad_request_message() {
        Assertions.assertEquals("Invalid authentication details", this.exceptionMessage);
    }

    @Then("System should authenticate the request")
    public void system_authenticate_the_request() throws URISyntaxException, JsonProcessingException, NoTransactionException, AccountException, ForbiddenException, BadRequestException, InvalidCustomerException {

        // Mock - Authentication Rest API Call
        CustomerDetails customerDetails = new CustomerDetails("mock_customer_id");
        ObjectMapper mapper = new ObjectMapper();
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(authenticateUrl)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(customerDetails))
                );

        //Mock Service method call
        List<CustomerTransactionResponse> responses = populateCustomerTransactionResponse();
        MockitoAnnotations.initMocks(this);
        Mockito.when(customerAccountTransactionService.getCustomerTransactions(
                any(CustomerTransactionRequest.class), any(HttpHeaders.class)))
                .thenReturn(responses);

        // Actual Call
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "mock_customer_id");
        CustomerTransactionRequest ctr = populateCustomerTransactionRequest();
        ResponseEntity<CustomerAccountTransactionResponse> responseEntity = controller.getCustomerTransactions(ctr, headers);

        // Assertions
        CustomerAccountTransactionResponse catr = responseEntity.getBody();
        Assertions.assertEquals(100d, catr.getDebitTotal());
    }

    @NotNull
    private CustomerTransactionRequest populateCustomerTransactionRequest() {
        CustomerTransactionRequest ctr = new CustomerTransactionRequest();
        ctr.setCustomerId(this.customerId);
        ctr.setCurrency("INR");
        ctr.setYear(2020);
        ctr.setMonth(10);
        return ctr;
    }

    private List<CustomerTransactionResponse> populateCustomerTransactionResponse() {
        List<CustomerTransactionResponse> responses = new ArrayList<>();
        CustomerTransactionResponse response = new CustomerTransactionResponse();
        response.setTransactionType("Debit");
        response.setAmount(100d);
        responses.add(response);

        return responses;
    }
}
