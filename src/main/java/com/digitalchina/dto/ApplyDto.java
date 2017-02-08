package com.digitalchina.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Snail on 2016/12/30.
 */
public class ApplyDto {
    Integer sourceId;
    Map<Integer, Integer> apiMap;

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Map<Integer, Integer> getApiMap() {
        return apiMap;
    }

    public void setApiMap(Map<Integer, Integer> apiMap) {
        this.apiMap = apiMap;
    }
}
