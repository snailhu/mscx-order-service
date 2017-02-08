package com.digitalchina.common.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.digitalchina.common.util.ChangeConstants;
import com.digitalchina.common.util.HttpClientUtil;
import com.digitalchina.config.CallUrlConfig;
import com.digitalchina.config.PackageConfig;
import com.digitalchina.config.SourceConfig;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.*;
import com.digitalchina.exception.PlaceOrderException;
import com.digitalchina.resttemplate.ribbon.retryable.RetryableLoadbalancedRestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Snail on 2016/12/1.
 */
@Service
public class ISourceServieImpl{


    @Autowired
    CallUrlConfig callUrlConfig;

    @Autowired
    RetryableLoadbalancedRestTemplateUtil restTemplateUtil;

    /**
     * 获取套餐资源
     * @param idList
     * @return
     */
    public  Map<String,PackageDto>  getSourcePackageMap(List<SourcePackage> idList, UserData userData){
        System.out.print("11111111111111111111111111111111");
        Map<String,PackageDto> packageDtoMap =null;
        try {
        JSONArray param = (JSONArray) JSONObject.toJSON(idList);
        HttpHeaders headers = new HttpHeaders();
        headers.add("mscx-user-data",JSONObject.toJSONString(userData));
        headers.add("mscx-area-code",userData.getArea());

        String response =restTemplateUtil.post(callUrlConfig.getApiHost(),callUrlConfig.getApiServiceDetailUrl(),idList,headers);
        System.out.print("11111111111111111133333333333331"+response);
        JSONObject parse = (JSONObject) JSONObject.parse(response);
        JSONArray array = parse.getJSONArray("result");
        if(parse.getString("code").equals(ChangeConstants.ERROR_CODE)) throw new RuntimeException(parse.getString("message"));
        packageDtoMap =  new HashMap<>();
        if(array==null || array.size()==0)throw new PlaceOrderException(ChangeConstants.ERROR_CODE,"资源不存在");
        for(int i=0; i<array.size(); i++){
            if(array.getJSONObject(i)==null)throw new PlaceOrderException(ChangeConstants.ERROR_CODE,"资源不存在");
            JSONObject temp = array.getJSONObject(i);
            PackageDto packageDto = new PackageDto();
            packageDto.setDealyTime(temp.getInteger("monthLimit"));
            packageDto.setChargeMethod(temp.getString("chargeMethod"));
            packageDto.setPackageType(temp.getString("type"));
            packageDto.setSourceId(temp.getInteger("apiServiceId"));
            packageDto.setDefaultUseTime(temp.getIntValue("countLimit"));
            packageDto.setPackagePrice(temp.getDoubleValue("price"));
            packageDto.setPackageName(temp.getString("apiName"));
            packageDto.setPackageId(temp.getIntValue("chargeRuleId"));
            packageDto.setArea(temp.getString("area"));
            packageDto.setFree(this.ifFree(temp.getString("chargeType")));
            packageDto.setChargeType(temp.getString("chargeType"));
            packageDto.setAccessCnt(temp.getString("accessCnt"));
            packageDto.setApplyCnt(temp.getString("applyCnt"));
            packageDto.setLogoUrl(temp.getString("logoUrl"));
            packageDto.setChargeType(temp.getString("chargeType"));
            packageDto.setStatus(temp.getString("status"));
            packageDto.setApiServiceDesc(temp.getString("apiServiceDesc"));
            packageDto.setUnitPrice(temp.getString("unitPrice"));
            packageDto.setCountLimit(temp.getIntValue("countLimit"));
            packageDto.setApiJson(temp.toJSONString());
            packageDto.setCharRuleName(temp.getString("charge"));
            packageDto.setType(temp.getString("type"));
            packageDtoMap.put(temp.getInteger("apiServiceId")+""+temp.getIntValue("chargeRuleId"),packageDto);
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        return packageDtoMap;
    }
    /**
     * 获取数据资源
     * @param ids
     * @return
     */
    public  Map<Integer,SourceDto>  getSourceMap(List<Integer> ids ){
        if(ids==null&&ids.size()==0)throw new PlaceOrderException(ChangeConstants.ERROR_CODE,"没有成功支付的数据订单");
        String url = String.format("%s?dataIds=%s", callUrlConfig.getDataListUrl(), getParameters(ids));
        String response =restTemplateUtil.get(callUrlConfig.getDataHost(),url,null);
        JSONObject parse = (JSONObject) JSONObject.parse(response);
        if(parse.getString("code").equals(ChangeConstants.ERROR_CODE)) throw new PlaceOrderException(ChangeConstants.ERROR_CODE,parse.getString("message"));
        Map<Integer,SourceDto> sourceDtoMap =  new HashMap<>();
        JSONArray array = parse.getJSONArray("result");
        if(array==null||array.size()==0)return sourceDtoMap;
        for(int i=0; i<array.size(); i++){
            double price=0;
            if(array.getJSONObject(i)==null)throw new PlaceOrderException(ChangeConstants.ERROR_CODE,"资源不存在");
            JSONObject temp = array.getJSONObject(i);
            SourceDto sourceDto = new SourceDto();
            sourceDto.setSourceId(temp.getInteger("id"));
            sourceDto.setFree(this.ifFree(temp.getString("chargeType")));
            sourceDto.setArea(temp.getString("area"));
            sourceDto.setDesc(temp.getString("description"));
            sourceDto.setCreatedBy(temp.getString("createdBy"));
            sourceDto.setOrgName(temp.getString("orgName"));
            sourceDto.setUpdateTime(temp.getString("updatedTime"));
            sourceDto.setSourceJson(temp.toJSONString());
            if(temp.getString("price")!=null){
                price = Double.parseDouble(temp.getString("price"));
            }
            sourceDto.setSourcePrice(price);
            sourceDto.setSourceName(temp.getString("name"));
            sourceDto.setSourceType(ChangeConstants.DATA_TYPE);
            sourceDto.setChargeType(temp.getString("chargeType"));
            sourceDto.setStatus(temp.getString("status"));
            sourceDtoMap.put(temp.getIntValue("id"),sourceDto);
        }
        return sourceDtoMap;
    }

    /**
     * 获取微服务的资源详情
     * @param idList
     * @param userData
     * @return
     */
    public  Map<String,WeiAppDto>  getWeiAppPackageMap(List<WeiAppPackage> idList, UserData userData){
        JSONArray param = (JSONArray) JSONObject.toJSON(idList);
        HttpHeaders headers = new HttpHeaders();
        headers.add(ChangeConstants.MSCX_USER_DATA,JSONObject.toJSONString(userData));
        headers.add(ChangeConstants.MSCX_AREA_CODE,userData.getArea());
        String response =restTemplateUtil.post(callUrlConfig.getAppHost(),callUrlConfig.getAppServiceDetailUrl(),idList,headers);
        JSONObject parse = (JSONObject) JSONObject.parse(response);
        JSONArray array = parse.getJSONArray("result");
        if(parse.getString("code").equals(ChangeConstants.ERROR_CODE)) throw new RuntimeException(parse.getString("message"));
        Map<String,WeiAppDto> weiAppDtoMap =  new HashMap<>();
        if(array==null || array.size()==0)throw new PlaceOrderException(ChangeConstants.ERROR_CODE,"资源不存在");
        for(int i=0; i<array.size(); i++){
            if(array.getJSONObject(i)==null)throw new PlaceOrderException(ChangeConstants.ERROR_CODE,"资源不存在");
            JSONObject temp = array.getJSONObject(i);
            WeiAppDto weiAppDto = new WeiAppDto();
            weiAppDto.setArea(temp.getString("area"));
            weiAppDto.setChargeStatus(temp.getString("chargeStatus"));
            weiAppDto.setChargeType(temp.getString("chargeType"));
            weiAppDto.setFree(this.ifFree(temp.getString("chargeType")));
            weiAppDto.setDesc(temp.getString("description"));
            weiAppDto.setSourceId(temp.getInteger("appId"));
            weiAppDto.setSourcePrice(temp.getDoubleValue("price"));
            weiAppDto.setSourceName(temp.getString("appName"));
            weiAppDto.setSourceType(ChangeConstants.WAI_APP_TYPE);
            weiAppDto.setSourceJson(temp.toJSONString());
            weiAppDto.setStatus(temp.getString("appStatus"));
            weiAppDto.setChargeMethod(temp.getString("chargeMethod"));
            weiAppDto.setCountLimit(temp.getIntValue("boughtLimit"));
            weiAppDto.setChargeRuleDesc(temp.getString("chargeRuleDesc"));
            weiAppDto.setChargeRuleName(temp.getString("chargeRuleName"));
            weiAppDto.setPackageId(temp.getIntValue("chargeRuleId"));
            weiAppDtoMap.put(temp.getInteger("appId")+""+temp.getIntValue("chargeRuleId"),weiAppDto);
        }
        return weiAppDtoMap;
    }

    private String getParameters(List<Integer> ids){
        StringBuilder param = new StringBuilder();
        for(Integer temp : ids){
            param.append(",").append(temp);
        }
        return param.toString().substring(1);
    }
    private boolean ifFree(String type){
        if(type.equals(ChangeConstants.CHARGE_TYPE_FREE)){
            return  true;
        }else{
            return  false;
        }
    }

    //获取资源的接入量和浏览量
    public ConcurrentHashMap<Integer,ViewAndApplyDto> getViewApply(List<Integer> ids, UserData userData){
        if(ids==null&&ids.size()==0)throw new PlaceOrderException(ChangeConstants.ERROR_CODE,"没有购买的资源");
        HttpHeaders headers = new HttpHeaders();
        headers.add(ChangeConstants.MSCX_USER_DATA,JSONObject.toJSONString(userData));
        headers.add(ChangeConstants.MSCX_AREA_CODE,userData.getArea());
        String url = String.format("%s?idList=%s", callUrlConfig.getApiBroAndApl(), getParameters(ids));
        String response =restTemplateUtil.get(callUrlConfig.getApiHost(),url,headers);
        JSONObject parseId = (JSONObject) JSONObject.parse(response);
        JSONArray array = parseId.getJSONArray("result");
        if(parseId.getString("code").equals(ChangeConstants.ERROR_CODE)) throw new RuntimeException(parseId.getString("message"));
        ConcurrentHashMap<Integer,ViewAndApplyDto> sourceDtoMap =  new ConcurrentHashMap<>();
        if(array==null||array.size()==0)return sourceDtoMap;
        for(int i=0; i<array.size(); i++){
            double price=0;
            if(array.getJSONObject(i)==null)throw new PlaceOrderException(ChangeConstants.ERROR_CODE,"资源不存在");
            JSONObject temp = array.getJSONObject(i);
            ViewAndApplyDto viewAndApplyDto = new ViewAndApplyDto();
            viewAndApplyDto.setApplyCnt(temp.getInteger("applyCnt"));
            viewAndApplyDto.setStatus(temp.getInteger("status"));
            viewAndApplyDto.setViewCnt(temp.getInteger("viewCnt"));
            sourceDtoMap.put(temp.getIntValue("apiServiceId"),viewAndApplyDto);
        }
        return sourceDtoMap;
    }
}
