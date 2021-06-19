package com.mycompany.api.ebankingPortal.customer;

import com.mycompany.api.ebankingPortal.exception.NoTransactionException;

public interface CustomerAccountService {
    CustomerAccountDetails getCustomerAccountDetails(String customerId, String currency) throws NoTransactionException;
}
