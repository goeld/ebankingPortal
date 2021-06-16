package com.mycompany.api.ebankingPortal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.I_AM_A_TEAPOT, reason = "Service Provider is unable to serve the request")
public class ServiceProviderException extends Exception{

    public ServiceProviderException(String message) {
        super(message);
    }

}
