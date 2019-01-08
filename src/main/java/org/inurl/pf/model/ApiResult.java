package org.inurl.pf.model;

/**
 * @author raylax
 */
public class ApiResult {

    private boolean success;

    private String reason;

    private Object data;


    public static ApiResult ok() {
        return ok(null);
    }

    public static ApiResult ok(Object data) {
        ApiResult r = new ApiResult();
        r.success = true;
        r.data = data;
        return r;
    }

    public static ApiResult fail(String reason) {
        ApiResult r = new ApiResult();
        r.success = false;
        r.reason = reason;
        return r;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
