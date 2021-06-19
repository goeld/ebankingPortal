package com.mycompany.api.ebankingPortal.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private String acctIban;
    private Integer txnMonth;
    private Integer txnYear;
    private Integer pageNo;
    private Integer pageSize;
}
