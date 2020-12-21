package com.offcn.project.enums;

public enum  ProjectReturnEnume {
    VIRTUAL((byte)0,"虚拟货物"),
    REAL((byte)1,"实物回报");

    private byte code ;
    private String type ;

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    ProjectReturnEnume(byte code, String type) {
        this.code = code;
        this.type = type;
    }

    ProjectReturnEnume() {
    }
}
