package com.mycompany.api.ebankingPortal.customerAccountTransaction;

import com.mycompany.api.ebankingPortal.customer.AccountException;
import com.mycompany.api.ebankingPortal.exception.NoTransactionException;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Map;

public interface CustomerAccountTransactionService {
    List<CustomerTransactionResponse> getCustomerTransactions(CustomerTransactionRequest customerTransactionRequest, HttpHeaders headers) throws AccountException, NoTransactionException;
}
