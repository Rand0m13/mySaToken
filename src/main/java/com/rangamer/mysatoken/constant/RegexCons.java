package com.rangamer.mysatoken.constant;

/**
 * @author jesse
 * @date 2021年12月16日 18:18
 */
public interface RegexCons {

    /**
     * 4-12位，必须字母开头
     */
    public final String account = "[a-zA-Z][a-zA-Z0-9]{3,11}";

    /**
     * 手机号
     */
    public final String phone = "^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";

    /**
     * 密码 以字母开头，长度在6~18之间，只能包含字母、数字和下划线
     */
    public final String password = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 邮箱
     */
    public final String mail = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

}
