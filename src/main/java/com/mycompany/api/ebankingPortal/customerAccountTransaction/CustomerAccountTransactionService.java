package com.mycompany.api.ebankingPortal.customerAccountTransaction;

import com.mycompany.api.ebankingPortal.customer.AccountException;
import com.mycompany.api.ebankingPortal.exception.NoTransactionException;

import java.util.List;
import java.util.Map;

public interface CustomerAccountTransactionService {
    List<CustomerTransactionResponse> getCustomerTransactions(CustomerTransactionRequest customerTransactionRequest, Map<String, String> headers) throws AccountException, NoTransactionException;
}
