package com.weiweicode.crm.exceptions;

/**
 * @Author weiwei
 * @Date 2023/1/4 19:44
 * @Version 1.0
 */
public class LoginException extends Exception{
    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }
}
