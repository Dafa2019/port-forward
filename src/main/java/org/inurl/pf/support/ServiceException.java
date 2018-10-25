package org.inurl.pf.support;

public class ServiceException extends RuntimeException {

    private String message;

    public ServiceException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
