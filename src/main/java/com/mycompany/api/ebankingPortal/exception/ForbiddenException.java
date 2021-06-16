package com.mycompany.api.ebankingPortal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Access to resource is forbidden")
public class ForbiddenException extends Exception {

    public ForbiddenException(String message) {
        super(message);
    }
}
