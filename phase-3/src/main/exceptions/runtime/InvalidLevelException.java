package main.exceptions.runtime;

public class InvalidLevelException extends IllegalArgumentException {
    public InvalidLevelException(String msg) {
        super(msg);
    }
}
