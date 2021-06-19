package com.mycompany.api.ebankingPortal.customer;

import com.mycompany.api.ebankingPortal.exception.NoTransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerAccountServiceImpl implements CustomerAccountService {

    private static Logger logger = LoggerFactory.getLogger(CustomerAccountServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${services.url.customer-account-service-customer-currency}")
    private String customerAccountServiceCustomerCurrencyUrl;

    @Value("${services.url.customer-account-service-customer}")
    private String customerAccountServiceCustomerUrl;

    @Override
    public CustomerAccountDetails getCustomerAccountDetails(String customerId, String currency) throws NoTransactionException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity(headers);


        ResponseEntity<CustomerAccountDetails> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(customerAccountServiceCustomerCurrencyUrl,
                    HttpMethod.GET, null,
                    CustomerAccountDetails.class, customerId, currency);
        } catch (Exception ex) {
            throw new NoTransactionException("No transactions found");
        }

        return responseEntity.getBody();
    }
}
