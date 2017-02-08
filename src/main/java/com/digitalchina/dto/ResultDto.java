package com.digitalchina.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snail on 2016/11/30.
 */
public class ResultDto<T> {

    int totalData;
    int totalPage;
    List<T> ResultDtoLists = new ArrayList<>();

    public int getTotalData() {
        return totalData;
    }

    public void setTotalData(int totalData) {
        this.totalData = totalData;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getResultDtoLists() {
        return ResultDtoLists;
    }

    public void setResultDtoLists(List<T> resultDtoLists) {
        ResultDtoLists = resultDtoLists;
    }
}
