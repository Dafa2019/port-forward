package org.inurl.pf.model;

public class ApiResult {

    private boolean success;

    private String reason;

    private Object data;


    public static ApiResult ok() {
        return ok(null);
    }

    public static ApiResult ok(Object data) {
        ApiResult r = new ApiResult();
        r.success = false;
        r.data = data;
        return r;
    }

    public static ApiResult fail(String reason) {
        ApiResult r = new ApiResult();
        r.success = false;
        r.reason = reason;
        return r;
    }

}
