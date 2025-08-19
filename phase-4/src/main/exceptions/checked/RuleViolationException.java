package main.exceptions.checked;

public class RuleViolationException extends Exception {
    public RuleViolationException(String message) {
        super(message);
    }
}
