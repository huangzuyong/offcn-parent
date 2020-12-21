package com.offcn.project.enums;

import lombok.Data;

public enum  ProjectImageTypeEnume {

    HEADER((byte)0 ,"头图"),
    DETAILS((byte)1,"详细图");

    private byte code ;
    private String msg ;


    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ProjectImageTypeEnume(byte code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    ProjectImageTypeEnume() {
    }
}
