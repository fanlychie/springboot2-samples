package org.fanlychie.security.jwt.model;

import lombok.Data;
import org.fanlychie.security.jwt.enums.ResponseEnum;

@Data
public class ResponseResult {

    private int errcode;

    private String errmsg;

    private Object data;

    ResponseResult(int errcode, String errmsg, Object data) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.data = data;
    }

    public static ResponseResult error(int errcode, String errmsg) {
        return new ResponseResult(errcode, errmsg, null);
    }

    public static ResponseResult error(ResponseEnum responseEnum) {
        return new ResponseResult(responseEnum.getCode(), responseEnum.getMessage(), null);
    }

    public static ResponseResult success(Object data) {
        return new ResponseResult(0, null, data);
    }

}