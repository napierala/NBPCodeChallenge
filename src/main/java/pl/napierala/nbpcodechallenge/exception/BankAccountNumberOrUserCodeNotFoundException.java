package pl.napierala.nbpcodechallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BankAccountNumberOrUserCodeNotFoundException extends RuntimeException {

    public BankAccountNumberOrUserCodeNotFoundException() {
    }

    public BankAccountNumberOrUserCodeNotFoundException(String message) {
        super(message);
    }

    public BankAccountNumberOrUserCodeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BankAccountNumberOrUserCodeNotFoundException(Throwable cause) {
        super(cause);
    }

    public BankAccountNumberOrUserCodeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}