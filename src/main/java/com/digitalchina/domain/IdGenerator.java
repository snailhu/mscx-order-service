package com.digitalchina.domain;

/**
 * id 生成计数器
 */
public class IdGenerator {
    private Long id;

    private String group;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group == null ? null : group.trim();
    }
}