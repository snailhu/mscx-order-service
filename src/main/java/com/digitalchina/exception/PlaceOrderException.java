package com.digitalchina.exception;

/**
 * Created by Snail on 2016/12/18.
 */
public class PlaceOrderException extends RuntimeException {

    private String code;

    public PlaceOrderException(String message) {
        super(message);
    }

    public PlaceOrderException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String toString() {
        return "PlaceOrderException(code=" + this.getCode() + ")";
    }

    public String getCode() {
        return this.code;
    }
}
