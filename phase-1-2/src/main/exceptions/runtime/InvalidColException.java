package main.exceptions.runtime;

public class InvalidColException extends IllegalArgumentException {
    public InvalidColException(String msg) {
        super(msg);
    }
}
