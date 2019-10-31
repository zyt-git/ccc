package com.fh.shop.api.common;

public enum ResponseEnum {
   MEMBER_HANDER_IS_LOSE(2002,"头信息缺失"),
   MEMBER_HANDER_IS_NULL(2001,"头信息不存在"),
   MEMBER_HANDER_IS_ERROR(2003,"头信息被篡改"),
   MEMBER_HANDER_IS_EXPIRE(2004,"头信息超时"),

   SHOP_IS_NULL(3001,"商品不存在"),
   SHOP_IS_STATUS(3002,"商品没有上架"),



   CART_IS_NULL(4001,"购物车为空"),
   CART_CARTITEM_IS_NULL(4002,"购物车中商品不存在"),

    TOKEN_HANDER_IS_NULL(5003,"token头信息不存在"),
    TOKEN_HANDER_IS_LOSE(5002,"token头信息不存在"),
   TOKEN_HANDER_IS_REPET(5001,"请求重复发送"),

    SHOP_STOCK_IS_SHORT(6001,"商品的库存不足"),

    PAYLOG_IS_NULL(7001,"支付日志不存在"),

    PAY_SHOP_STSTUS_IX_EXPIRE(7002,"支付二维码过期"),



    MEMBER_PHONE_NOT_REGISTER(1019,"没有注册账户"),
    MEMBER_PASSWORD_IS_ERROR(1018,"会员密码错误"),
    MEMBER_NAME_IS_EXIST(1017,"会员不存在"),
    MEMBER_EMAIL_EXIST(1016,"会员邮箱已存在"),
    MEMBER_EMAIL_ISNULL(1015,"会员邮箱不能为空"),
    MEMBER_PHONE_STYLE_ERROE(1013,"会员手机号格式错误"),
    MEMBER_SMS_ISEXPIRE(1014,"会员验证码超时"),
    MEMBER_PHONE_EXist(1012,"会员手机号已存在"),
    MEMBER_NAME_EXist(1011,"会员名已存在"),
    MEMBER_PASSWORD_ISNULL(1010,"会员密码不能为空"),
    MEMBER_PHONE_ISNULL(1009,"会员手机号不能为空"),
    MEMBER_NAME_ISNULL(1008,"会员名称不能为空"),
    REGISTER_NOTE_IS_ERROR(1007,"短信验证码不正确"),
    REGISTER_NOTE_IS_NULL(1006,"短信验证码为空"),
    IDS_IS_NULL(2001,"品牌id不能为空");
    private  Integer code;
    private String msg;

    ResponseEnum(){}
    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
