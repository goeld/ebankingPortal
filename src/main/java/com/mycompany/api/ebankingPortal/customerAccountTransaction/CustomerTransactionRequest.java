package com.mycompany.api.ebankingPortal.customerAccountTransaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class CustomerTransactionRequest {

    private String customerId;
    private String currency;
    @JsonIgnore
    private String acctIban;
    private Integer year;
    private Integer month;
    private Integer pageNo;
    private Integer pageSize;
}
