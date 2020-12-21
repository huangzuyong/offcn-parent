package com.offcn.dycommon.enums;

public enum ProjectStatusEnume {

    DRAFT((byte)0,"草稿"),
    SUBMIT_AUTH((byte)1,"提交审核申请"),
    AUTHING((byte)2,"后台正在审核"),
    AUTHED((byte)3,"后台审核通过"),
    AUTHFAIL((byte)4,"审核失败"),
    STARTING((byte)5,"开始众筹"),//新增众筹项目一些状态
    SUCCESS((byte)6,"众筹成功"),
    FINISHED((byte)7,"众筹完成"),
    FAIL((byte)8,"众筹失败");


    private Byte code ;
    private String type ;

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    ProjectStatusEnume() {
    }

    ProjectStatusEnume(Byte code, String type) {
        this.code = code;
        this.type = type;
    }
}
