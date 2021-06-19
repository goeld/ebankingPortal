package com.mycompany.api.ebankingPortal.customerAccount;

import com.mycompany.api.ebankingPortal.transaction.NoTransactionException;

public interface CustomerAccountService {
    CustomerAccountDetails getCustomerAccountDetails(String customerId, String currency) throws NoTransactionException;
}
