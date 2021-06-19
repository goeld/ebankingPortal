package com.mycompany.api.ebankingPortal.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "No Contents")
public class NoTransactionException extends Exception {
    public NoTransactionException(String message) {
        super(message);
    }
}
