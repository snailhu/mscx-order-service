package com.digitalchina.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 请求参数构建
 * @author zhang
 *
 */
public class RequestParamBuilder {
	
	private Map<String, String> map=new HashMap<String, String>();
	
	public static RequestParamBuilder getInstance(){
		return new RequestParamBuilder();
	}
	
	public RequestParamBuilder add(String key,String value){
		map.put(key, value);
		return this;
	}
	
	public Map<String, String> toMap(){
		return map;
	}
	
	public String build(){
		if(map==null){
			return null;
		}
		
		Iterator<Entry<String,String>> iterator=map.entrySet().iterator();
		
		StringBuffer sBuffer=new StringBuffer();
		while(iterator.hasNext()){
			Entry<String, String> entry=iterator.next();
			sBuffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		
		String result=sBuffer.toString();
		//if(StringUtil.isNotEmpty(result)){
		if(!("".equals(result))){
			result=result.substring(0,result.length()-1);
		}
		
		return result;
	}
}
