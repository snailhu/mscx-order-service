package com.digitalchina.dao;

import com.digitalchina.domain.ApiOfflineMeet;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by lubin .
 */
@Mapper
public interface ApiOfflineMeetDao {

	int insert(ApiOfflineMeet apiOfflineMeet);
}
