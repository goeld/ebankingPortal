package com.mycompany.api.ebankingPortal.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Invalid customer Exception")
public class InvalidCustomerException extends Exception {

    public InvalidCustomerException(String message) {
        super(message);
    }
}
