package com.digitalchina.common;

/**
 * Created by Snail on 2016/12/9.
 */
public enum OrderType {
    ONLINE_PAY("线上支付"){
        @Override
        public String getIndex() {
            return "1";
        }
    }, OUTLINT_PAY("线下支付"){
        @Override
        public String getIndex() {
            return "2";
        }
    };


    private String name ;
    private  String index ;
    public abstract String  getIndex();


    private OrderType( String name ){
        this.name = name ;
        //this.index = index ;
    }



}
