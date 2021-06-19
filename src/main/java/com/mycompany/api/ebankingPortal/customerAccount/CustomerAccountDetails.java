package com.mycompany.api.ebankingPortal.customerAccount;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAccountDetails {
    private String accountId;
    private String customerId;
    private String accountCurrency;
}
