package com.mycompany.api.ebankingPortal.customerAccount;

import com.mycompany.api.ebankingPortal.authentication.InvalidCustomerException;
import com.mycompany.api.ebankingPortal.exception.BadRequestException;
import com.mycompany.api.ebankingPortal.transaction.NoTransactionException;

public interface CustomerAccountService {
    CustomerAccountDetails getCustomerAccountDetails(String customerId, String currency) throws NoTransactionException, BadRequestException, InvalidCustomerException;
}
