package com.xuzhili.xhttprequestdemo;

/**
 * Created by $xuzhili on 2018/6/6.
 * hnzkxuzhili@gmail.com
 */

public class HttpResponse {

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
