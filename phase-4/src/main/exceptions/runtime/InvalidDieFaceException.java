package main.exceptions.runtime;

public class InvalidDieFaceException extends IllegalArgumentException {
    public InvalidDieFaceException(String msg) {
        super(msg);
    }

}
