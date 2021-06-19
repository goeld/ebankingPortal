package com.mycompany.api.ebankingPortal.authentication;

import com.mycompany.api.ebankingPortal.exception.BadRequestException;
import com.mycompany.api.ebankingPortal.exception.ForbiddenException;
import com.mycompany.api.ebankingPortal.exception.InvalidCustomerException;
import org.springframework.http.HttpHeaders;

import java.util.Map;

public interface AuthenticationValidationService {
    public CustomerDetails validateAndGetCustomer(HttpHeaders headers, String customerId) throws BadRequestException, InvalidCustomerException;
}
