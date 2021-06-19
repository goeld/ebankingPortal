package com.mycompany.api.ebankingPortal.customerAccountTransaction;

import com.mycompany.api.ebankingPortal.authentication.AuthenticationValidationService;
import com.mycompany.api.ebankingPortal.authentication.CustomerDetails;
import com.mycompany.api.ebankingPortal.customer.AccountException;
import com.mycompany.api.ebankingPortal.exception.BadRequestException;
import com.mycompany.api.ebankingPortal.exception.ForbiddenException;
import com.mycompany.api.ebankingPortal.exception.NoTransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CustomerAccountTransactionController {

    private static Logger logger = LoggerFactory.getLogger(CustomerAccountTransactionController.class);

    @Autowired
    private AuthenticationValidationService authenticationValidationService;

    @Autowired
    private CustomerAccountTransactionService service;


    @PostMapping("/ebanking/customer/transactions")
    public ResponseEntity<CustomerAccountTransactionResponse> getCustomerTransactions(
            @RequestBody CustomerTransactionRequest customerTransactionRequest,
            @RequestHeader Map<String, String> headers) throws AccountException, ForbiddenException, BadRequestException, NoTransactionException {

        logger.debug("Get Customer Transactions start");
        CustomerDetails customerDetails = validateCustomer(customerTransactionRequest, headers);
        customerTransactionRequest.setCustomerId(customerDetails.getCustomerId());


        logger.info("Customer validation is successful, getting customer transactions - start");
        List<CustomerTransactionResponse> responses = service.getCustomerTransactions(customerTransactionRequest, headers);
        logger.info("Customer validation is successful, getting customer transactions - ends");

        if (CollectionUtils.isEmpty(responses)) {
            throw new NoTransactionException("No Transactions found");
        }

        CustomerAccountTransactionResponse catr = this.getTransactionTotal(responses);
        ResponseEntity<CustomerAccountTransactionResponse> responseEntity = new ResponseEntity<CustomerAccountTransactionResponse>(catr, HttpStatus.OK);


        logger.debug("Get Customer Transactions ends");
        return responseEntity;
    }

    private CustomerAccountTransactionResponse getTransactionTotal(List<CustomerTransactionResponse> response) {
        CustomerAccountTransactionResponse catr = new CustomerAccountTransactionResponse();
        catr.setCustomerTransactionResponses(response);
        catr.setCreditTotal(0d);
        catr.setDebitTotal(0d);

        response.forEach(ctr -> {
            if (ctr.getTransactionType().equalsIgnoreCase("Debit")) {
                catr.setDebitTotal(catr.getDebitTotal() + ctr.getAmount());
            } else if (ctr.getTransactionType().equalsIgnoreCase("Credit")) {
                catr.setCreditTotal(catr.getCreditTotal() + ctr.getAmount());
            }
        });

        return catr;
    }


    private CustomerDetails validateCustomer(CustomerTransactionRequest customerTransactionRequest, Map<String, String> headers) throws ForbiddenException, BadRequestException {
        CustomerDetails customerDetails = authenticationValidationService.validateAngGetCustomer(headers);

        if (customerDetails.getCustomerId() == null || !customerDetails.getCustomerId().equals(customerTransactionRequest.getCustomerId())) {
            throw new ForbiddenException("Invalid Customer");
        }

        if (customerTransactionRequest.getCurrency() == null || customerTransactionRequest.getCurrency().trim().length() == 0) {
            throw new BadRequestException("Currency is missing");
        }

        if (customerTransactionRequest.getYear() == null || customerTransactionRequest.getYear() < 0) {
            throw new BadRequestException("Invalid Year");
        }

        if (customerTransactionRequest.getMonth() == null || customerTransactionRequest.getMonth() < 0) {
            throw new BadRequestException("Invalid Month");
        }

        return customerDetails;
    }
}
