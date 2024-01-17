package site.hyundai.wewrite.global.exeception.service;

public class NotAuthorizedUserException extends DefaultException {
    public NotAuthorizedUserException(String message) {
        super(message);
    }
}