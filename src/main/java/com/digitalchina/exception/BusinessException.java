package com.digitalchina.exception;

/**
 * @author owen
 * @since 2016/12/5 9:44
 */
public class BusinessException extends RuntimeException {
    private String message;

    public BusinessException(String message){
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}