package com.offcn.dycommon.enums;

public enum OrderStatusEnumes {
    UNPAY((byte)1,"未支付"),
    CANCEL((byte)1,"订单取消"),
    PAYED((byte)2,"支付成功"),
    WAITING((byte)3,"等待发货"),
    SEND((byte)4,"发货中"),
    SENDED((byte)5,"已送达"),
    SUCCESS((byte)6,"交易完成"),
    FAIL((byte)7,"交易失败");

    private byte code ;
    private String status ;

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    OrderStatusEnumes() {
    }

    OrderStatusEnumes(byte code, String status) {
        this.code = code;
        this.status = status;
    }
}
