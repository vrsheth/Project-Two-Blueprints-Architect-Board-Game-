package main.exceptions.runtime;

public class InvalidDieMaterialException extends IllegalArgumentException {
    public InvalidDieMaterialException(String message) {
        super(message);
    }
}
