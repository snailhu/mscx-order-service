package com.digitalchina.dao;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IdGeneratorDao {

    public void increase();

    public int current();

}