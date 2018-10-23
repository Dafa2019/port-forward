package org.inurl.pf.support;

public class RouteException extends RuntimeException {

    private String message;

    public RouteException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
