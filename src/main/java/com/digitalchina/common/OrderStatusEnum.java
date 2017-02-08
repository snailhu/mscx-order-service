package com.digitalchina.common;

/**
 * Created by Snail on 2016/12/1.
 */
public enum OrderStatusEnum {

    NOT_PAY("未支付"){
        @Override
        public int getIndex() {
            return 1;
        }
    }, PAYING("支付中"){
        @Override
        public int getIndex() {
            return 2;
        }
    }, PAY_SUCCESS("已付款"){
        @Override
        public int getIndex() {
            return 3;
        }
    }, PAR_FAIL("付款失败"){
        @Override
        public int getIndex() {
            return 4;
        }
    };


    private String name ;
    private int index ;
    public abstract  int getIndex();

    public static String getStatus(int statusCode){
        switch (statusCode){
            case 1:
                return "未支付";
            case 2:
                return "支付中";
            case 3:
                return "已付款";
            case 4:
                return "付款失败";
        }
        return "ERROR";
    }



    private OrderStatusEnum( String name ){
        this.name = name ;
        //this.index = index ;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setIndex(int index) {
        this.index = index;
    }

}
