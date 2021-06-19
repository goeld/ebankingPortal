package com.mycompany.api.ebankingPortal.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthenticationValidationServiceImpl implements AuthenticationValidationService {

    private Logger logger = LoggerFactory.getLogger(AuthenticationValidationServiceImpl.class);

    @Override
    public CustomerDetails validateAngGetCustomer(Map<String, String> headers) {
        logger.info("Calling Auth service to validate token and get Customer");
        return new CustomerDetails("P-0123456789");
    }
}
