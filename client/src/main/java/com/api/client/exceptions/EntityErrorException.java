package com.api.client.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        code = HttpStatus.INTERNAL_SERVER_ERROR
)
public class EntityErrorException extends RuntimeException {

    public EntityErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
