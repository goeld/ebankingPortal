package com.mycompany.api.ebankingPortal.customerAccountTransaction;

import com.mycompany.api.ebankingPortal.exception.CustomerAccountException;
import com.mycompany.api.ebankingPortal.exception.NoTransactionException;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface CustomerAccountTransactionService {
    List<CustomerTransactionResponse> getCustomerTransactions(CustomerTransactionRequest customerTransactionRequest, HttpHeaders headers) throws CustomerAccountException, NoTransactionException;
}
