package com.mycompany.api.ebankingPortal.authentication;

import com.mycompany.api.ebankingPortal.exception.BadRequestException;
import org.springframework.http.HttpHeaders;

public interface AuthenticationValidationService {
    public CustomerDetails validateAndGetCustomer(HttpHeaders headers, String customerId) throws BadRequestException, InvalidCustomerException;
}
