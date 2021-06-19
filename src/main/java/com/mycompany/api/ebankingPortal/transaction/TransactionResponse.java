package com.mycompany.api.ebankingPortal.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class TransactionResponse {
    private String transactionId;
    private Double amount;
    private String currency;
    private String acctIBank;
    private LocalDate valueDate;
    private String description;

    public TransactionResponse(String transactionId, Double amount, String currency, String acctIBank,
                               LocalDate valueDate, String description) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.acctIBank = acctIBank;
        this.valueDate = valueDate;
        this.description = description;
    }
}
