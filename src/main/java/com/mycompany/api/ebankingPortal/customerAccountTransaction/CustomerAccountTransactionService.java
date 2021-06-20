package com.mycompany.api.ebankingPortal.customerAccountTransaction;

import com.mycompany.api.ebankingPortal.authentication.InvalidCustomerException;
import com.mycompany.api.ebankingPortal.customerAccount.CustomerAccountException;
import com.mycompany.api.ebankingPortal.exception.BadRequestException;
import com.mycompany.api.ebankingPortal.transaction.NoTransactionException;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface CustomerAccountTransactionService {
    List<CustomerTransactionResponse> getCustomerTransactions(CustomerTransactionRequest customerTransactionRequest, HttpHeaders headers) throws CustomerAccountException, NoTransactionException, BadRequestException, InvalidCustomerException;
}
