package com.mycompany.api.ebankingPortal.customerAccountTransaction;

import com.mycompany.api.ebankingPortal.customer.AccountException;
import com.mycompany.api.ebankingPortal.customer.CustomerAccountDetails;
import com.mycompany.api.ebankingPortal.customer.CustomerAccountService;
import com.mycompany.api.ebankingPortal.exception.NoTransactionException;
import com.mycompany.api.ebankingPortal.transaction.TransactionRequest;
import com.mycompany.api.ebankingPortal.transaction.TransactionResponse;
import com.mycompany.api.ebankingPortal.transaction.TransactionService;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CustomerAccountTransactionServiceImpl implements CustomerAccountTransactionService {

    private static Logger logger = LoggerFactory.getLogger(CustomerAccountTransactionServiceImpl.class);

    @Autowired
    private CustomerAccountService customerAccountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private RestTemplate restTemplate;

    private RequestResponseMapper requestResponseMapper = Mappers.getMapper(RequestResponseMapper.class);

    @Override
    public List<CustomerTransactionResponse> getCustomerTransactions(@Valid CustomerTransactionRequest customerTransactionRequest, Map<String, String> headers) throws AccountException, NoTransactionException {
        String customerId = customerTransactionRequest.getCustomerId();
        String currency = customerTransactionRequest.getCurrency();
        if (customerId == null) {
            //TODO : Throw exception
        }

        CustomerAccountDetails customerAccountDetails = getCustomerAccount(customerId, currency);
        customerTransactionRequest.setAcctIban(customerAccountDetails.getAccountId());
        List<CustomerTransactionResponse> response = getAccountTransactions(customerTransactionRequest);
        return response;
    }

    private List<CustomerTransactionResponse> getAccountTransactions(CustomerTransactionRequest customerTransactionRequest) {
        TransactionRequest transactionRequest = requestResponseMapper.toTransactionRequest(customerTransactionRequest);
        List<TransactionResponse> transactionResponse = transactionService.getTransactionAccounts(transactionRequest);

        List<CustomerTransactionResponse> customerTransactionResponses = new ArrayList<>();
        transactionResponse.forEach(tr -> {
            CustomerTransactionResponse ctr = requestResponseMapper.toCustomerTransactionResponse(tr);
            String transactionType = ctr.getAmount() > 0 ? "Credit" : "Debit";
            ctr.setTransactionType(transactionType);
            customerTransactionResponses.add(ctr);
        });

        return customerTransactionResponses;
    }

    public CustomerAccountDetails getCustomerAccount(String customerId, String currency) throws AccountException, NoTransactionException {
        logger.info("Get customer transaction - starts");

        CustomerAccountDetails customerAccountDetails = customerAccountService.getCustomerAccountDetails(customerId, currency);
        if (customerAccountDetails == null) {
            logger.info("No accounts found for the customer.");
            throw new AccountException("No accounts found for the customer ");
        }
        logger.info("Call to get customer accounts is completed");

        logger.info("Get customer transaction - ends");
        return customerAccountDetails;
    }

}