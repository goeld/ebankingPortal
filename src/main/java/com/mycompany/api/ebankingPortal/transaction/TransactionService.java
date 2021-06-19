package com.mycompany.api.ebankingPortal.transaction;

import java.util.List;

public interface TransactionService {
    List<TransactionResponse> getTransactionAccounts(TransactionRequest transactionRequest);
}
