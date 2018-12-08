package com.chenwei.zhifou.enums;

/**
 * @Author: wei1
 * @Date: Create in 2018/12/8 14:47
 * @Description:
 */
public enum ResultEnum {
    ENTITY_QUESTION(1, "问题实体"),

    ENTITY_COMMENT(2, "评论实体"),

    ANONYMOUS_USERID(3, "匿名用户");


    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

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
}
