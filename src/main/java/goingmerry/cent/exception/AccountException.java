package goingmerry.cent.exception;

public class AccountException extends BaseException {

    public AccountException(ErrorType errorType) {
        super(errorType);
    }
}