package com.mycompany.api.ebankingPortal.authentication;

import com.mycompany.api.ebankingPortal.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AuthenticationValidationServiceImpl implements AuthenticationValidationService {

    private Logger logger = LoggerFactory.getLogger(AuthenticationValidationServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${services.url.authenticate-service}")
    private String authenticateUrl;

    @Override
    public CustomerDetails validateAndGetCustomer(HttpHeaders headers, String customerId) throws BadRequestException, InvalidCustomerException {
        logger.info("Calling Auth service to validate token and get Customer");
        List<String> authorization = headers.get("authorization");

        if (CollectionUtils.isEmpty(authorization) || authorization.get(0) == null || authorization.get(0).trim().length() == 0) {
            logger.error("Invalid user request");
            throw new BadRequestException("Invalid authentication details");
        }

        try {
            ResponseEntity<CustomerDetails> responseEntity = restTemplate.exchange(authenticateUrl,
                    HttpMethod.GET, null,
                    CustomerDetails.class, authorization);
            logger.debug("Authentication token validated ");
            return new CustomerDetails(customerId);
        } catch (Exception ex) {
            logger.error("Error Authenticating customer:", ex);
            throw new InvalidCustomerException("Invalid Customer");
        }
    }
}
