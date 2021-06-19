package com.mycompany.api.ebankingPortal.authentication;

import java.util.Map;

public interface AuthenticationValidationService {
    CustomerDetails validateAngGetCustomer(Map<String, String> headers);
}
