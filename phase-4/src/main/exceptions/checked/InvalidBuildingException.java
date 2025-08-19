package main.exceptions.checked;

public class InvalidBuildingException extends RuleViolationException {
    public InvalidBuildingException(String message) {
        super(message);
    }
}
