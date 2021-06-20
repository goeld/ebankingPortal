package com.mycompany.api.ebankingPortal.customerAccountTransaction;

import com.mycompany.api.ebankingPortal.authentication.InvalidCustomerException;
import com.mycompany.api.ebankingPortal.customerAccount.CustomerAccountException;
import com.mycompany.api.ebankingPortal.customerAccount.CustomerAccountDetails;
import com.mycompany.api.ebankingPortal.customerAccount.CustomerAccountService;
import com.mycompany.api.ebankingPortal.exception.BadRequestException;
import com.mycompany.api.ebankingPortal.transaction.NoTransactionException;
import com.mycompany.api.ebankingPortal.transaction.TransactionRequest;
import com.mycompany.api.ebankingPortal.transaction.TransactionResponse;
import com.mycompany.api.ebankingPortal.transaction.TransactionService;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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
    public List<CustomerTransactionResponse> getCustomerTransactions(CustomerTransactionRequest customerTransactionRequest, HttpHeaders headers) throws CustomerAccountException, NoTransactionException, BadRequestException, InvalidCustomerException {
        String customerId = customerTransactionRequest.getCustomerId();
        String currency = customerTransactionRequest.getCurrency();

        CustomerAccountDetails customerAccountDetails = customerAccountService.getCustomerAccountDetails(customerId, currency);
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

}
