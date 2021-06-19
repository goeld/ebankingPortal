package com.mycompany.api.ebankingPortal.customerAccountTransaction;

import com.mycompany.api.ebankingPortal.transaction.TransactionRequest;
import com.mycompany.api.ebankingPortal.transaction.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface RequestResponseMapper {

    @Mappings({
            @Mapping(target = "txnMonth", source = "month"),
            @Mapping(target = "txnYear", source = "year")
    })
    TransactionRequest toTransactionRequest(CustomerTransactionRequest ctr);

    CustomerTransactionResponse toCustomerTransactionResponse(TransactionResponse tr);
}
