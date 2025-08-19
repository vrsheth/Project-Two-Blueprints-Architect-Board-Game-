package main.exceptions.checked;

public class InvalidPlacementException extends RuleViolationException {
    public InvalidPlacementException(String message) {
        super(message);
    }
}
