package com.digitalchina.dto;

import java.util.List;

/**
 * Created by Snail on 2017/1/18.
 */
public class DataAndIdsDto {
    String queryDate;
    List<String> userIds;

    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }
}
