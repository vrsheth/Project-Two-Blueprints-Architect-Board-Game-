package main.exceptions.runtime;

public class InvalidBlueprintTemplateException extends IllegalArgumentException {
    public InvalidBlueprintTemplateException(String msg) {
        super(msg);
    }

}
