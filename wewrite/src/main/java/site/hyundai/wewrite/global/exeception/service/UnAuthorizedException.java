package site.hyundai.wewrite.global.exeception.service;

public class UnAuthorizedException extends DefaultException{
    public UnAuthorizedException(String message) {
        super(message);
    }
}
