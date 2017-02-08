package com.digitalchina.dto;

/**
 * Created by Snail on 2016/12/1.
 */
public class OmitSourceDto {
    Integer sourceId;
    Integer char_rule_id;
    Integer item_num;
    String sourceType;

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }


    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getChar_rule_id() {
        return char_rule_id;
    }

    public void setChar_rule_id(Integer char_rule_id) {
        this.char_rule_id = char_rule_id;
    }

    public Integer getItem_num() {
        return item_num;
    }

    public void setItem_num(Integer item_num) {
        this.item_num = item_num;
    }
}
