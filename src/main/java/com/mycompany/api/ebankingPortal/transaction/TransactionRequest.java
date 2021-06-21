package com.mycompany.api.ebankingPortal.transaction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private String acctIban;
    private Integer txnMonth;
    private Integer txnYear;
    private Integer pageNo;
    private Integer pageSize;
}
