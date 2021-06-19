package com.mycompany.api.ebankingPortal.customer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No Account Found")
public class AccountException extends Exception {
    public AccountException(String message) {
        super(message);
    }
}
