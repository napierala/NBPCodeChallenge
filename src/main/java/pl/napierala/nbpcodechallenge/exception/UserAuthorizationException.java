package pl.napierala.nbpcodechallenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserAuthorizationException extends RuntimeException {

    public UserAuthorizationException() {
    }

    public UserAuthorizationException(String message) {
        super(message);
    }

    public UserAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAuthorizationException(Throwable cause) {
        super(cause);
    }

    public UserAuthorizationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}