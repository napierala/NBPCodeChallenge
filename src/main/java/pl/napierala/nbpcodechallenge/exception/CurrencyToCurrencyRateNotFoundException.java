package pl.napierala.nbpcodechallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CurrencyToCurrencyRateNotFoundException extends RuntimeException {

    public CurrencyToCurrencyRateNotFoundException() {
    }

    public CurrencyToCurrencyRateNotFoundException(String message) {
        super(message);
    }

    public CurrencyToCurrencyRateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CurrencyToCurrencyRateNotFoundException(Throwable cause) {
        super(cause);
    }

    public CurrencyToCurrencyRateNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}