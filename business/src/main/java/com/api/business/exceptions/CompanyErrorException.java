package com.api.business.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.INTERNAL_SERVER_ERROR
)
public class CompanyErrorException extends RuntimeException {

    public CompanyErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
