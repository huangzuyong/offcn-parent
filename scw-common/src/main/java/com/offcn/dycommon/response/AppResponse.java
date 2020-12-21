package com.offcn.dycommon.response;

import com.offcn.dycommon.enums.ResponseCodeEnume;

import java.security.interfaces.RSAKey;

public class AppResponse<T> {

    private Integer code ;
    private String msg ;
    private T data ;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static<T> AppResponse<T> ok(T data){

        AppResponse<T> response = new AppResponse<T>();
        response.setCode(ResponseCodeEnume.SUCCESS.getCode());
        response.setMsg(ResponseCodeEnume.SUCCESS.getMsg());
        response.setData(data);

        return response;

    }

    public static<T> AppResponse<T> fail(T data){

        AppResponse<T> response = new AppResponse<T>();
        response.setCode(ResponseCodeEnume.FAIl.getCode());
        response.setMsg(ResponseCodeEnume.FAIl.getMsg());
        response.setData(data);

        return response;

    }
}
