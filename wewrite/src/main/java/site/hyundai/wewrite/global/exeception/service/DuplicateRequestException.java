package site.hyundai.wewrite.global.exeception.service;

public class DuplicateRequestException extends DefaultException{
    public DuplicateRequestException(String message) {
        super(message);
    }
}
