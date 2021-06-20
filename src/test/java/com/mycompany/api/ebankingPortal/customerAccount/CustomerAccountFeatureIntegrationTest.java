package com.mycompany.api.ebankingPortal.customerAccount;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mycompany.api.ebankingPortal.authentication.AuthenticationValidationService;
import com.mycompany.api.ebankingPortal.authentication.CustomerDetails;
import com.mycompany.api.ebankingPortal.authentication.InvalidCustomerException;
import com.mycompany.api.ebankingPortal.customerAccountTransaction.CustomerAccountTransactionController;
import com.mycompany.api.ebankingPortal.customerAccountTransaction.CustomerAccountTransactionService;
import com.mycompany.api.ebankingPortal.customerAccountTransaction.CustomerTransactionRequest;
import com.mycompany.api.ebankingPortal.customerAccountTransaction.CustomerTransactionResponse;
import com.mycompany.api.ebankingPortal.exception.BadRequestException;
import com.mycompany.api.ebankingPortal.exception.ForbiddenException;
import com.mycompany.api.ebankingPortal.transaction.NoTransactionException;
import com.mycompany.api.ebankingPortal.utis.MockRestApiUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.platform.engine.Cucumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@Cucumber
//@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
public class CustomerAccountFeatureIntegrationTest {
    private String exceptionMessage;

    @InjectMocks
    @Autowired
    private CustomerAccountTransactionController controller;

    @Mock
    private AuthenticationValidationService authenticationValidationService;

    @Mock
    private CustomerAccountTransactionService customerAccountTransactionService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${services.url.authenticate-service}")
    private String authenticateUrl;

    private CustomerTransactionRequest customerTransactionRequest;

    private HttpHeaders headers;
    private String customerId;

    @Given("I am valid customer")
    public void i_am_valid_customer() {
        mock_authentication_validation_service();
        this.populateCustomerTransactionRequest();
        headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add("authorization", "mock_customer_id");
    }

    @When("I request for my accounts")
    public void request_my_accounts() throws NoTransactionException, ForbiddenException, CustomerAccountException, BadRequestException, InvalidCustomerException, URISyntaxException, JsonProcessingException {
        CustomerDetails customerDetails = new CustomerDetails("mock_customer_id");
        MockRestApiUtils.authenticationApiCall(authenticateUrl, restTemplate, HttpStatus.OK, customerDetails);
        mock_authentication_validation_service();
        exceptionMessage = Assertions.assertThrows(Exception.class, () -> {
            controller.getCustomerTransactions(customerTransactionRequest, headers);
        }).getMessage();
    }

    @When("currency is missing")
    public void currency_is_missing() {
        this.customerTransactionRequest.setCurrency(null);
    }

    @When("I request for my accounts with valid currency")
    public void request_my_accounts_with_valid_currency() throws NoTransactionException, ForbiddenException, CustomerAccountException, BadRequestException, InvalidCustomerException, URISyntaxException, JsonProcessingException {

//        // mock method calls
//        CustomerDetails customerDetails = new CustomerDetails("mock_customer_id");
//        MockRestApiUtils.authenticationApiCall(authenticateUrl, restTemplate, HttpStatus.OK, customerDetails);
//        mock_authentication_validation_service();
//        mock_customer_account_txn_service();
//        this.populateCustomerTransactionRequest();
//        this.customerTransactionRequest.setCurrency("INR");
        // TODO - NPE For mocked data.
//        controller.getCustomerTransactions(customerTransactionRequest, headers);

    }

    @Then("{string} message is shown to me")
    public void message_is_shown_to_me(String string) {
        Assertions.assertEquals(string, this.exceptionMessage);
    }

    @Then("My accounts are returned")
    public void my_accts_are_returned() {
//        System.out.println("");
    }

    private CustomerDetails mock_authentication_validation_service() {
        try {
            CustomerDetails details = new CustomerDetails();
            details.setCustomerId("mock_customer_id");
            authenticationValidationService = Mockito.mock(AuthenticationValidationService.class);
            Mockito.when(authenticationValidationService.validateAndGetCustomer(
                    any(HttpHeaders.class), any(String.class)))
                    .thenReturn(details);
        } catch (BadRequestException e) {
            e.printStackTrace();
        } catch (InvalidCustomerException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<CustomerTransactionResponse> mock_customer_account_txn_service() {
        try {
            List<CustomerTransactionResponse> responses = new ArrayList<>();
            MockitoAnnotations.initMocks(this);
            Mockito.when(customerAccountTransactionService.getCustomerTransactions(
                    any(CustomerTransactionRequest.class), any(HttpHeaders.class)
            ))
                    .thenReturn(responses);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void populateCustomerTransactionRequest() {
        this.customerTransactionRequest = new CustomerTransactionRequest();
        this.customerTransactionRequest.setCustomerId("mock_customer_id");
        this.customerTransactionRequest.setCurrency(null);
        this.customerTransactionRequest.setYear(2020);
        this.customerTransactionRequest.setMonth(10);
    }

    private List<CustomerTransactionResponse> populate_customer_txn_response() {
        List<CustomerTransactionResponse> responses = new ArrayList<>();
        CustomerTransactionResponse response = new CustomerTransactionResponse();
        response.setAmount(100d);
        response.setTransactionType("Debit");

        responses.add(response);
        return responses;
    }

}
