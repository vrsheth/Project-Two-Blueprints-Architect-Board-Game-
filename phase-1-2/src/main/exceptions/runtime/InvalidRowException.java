package main.exceptions.runtime;

public class InvalidRowException extends IllegalArgumentException {
    public InvalidRowException(String msg) {
        super(msg);
    }
}
