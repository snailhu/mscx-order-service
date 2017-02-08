package com.digitalchina.common.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.digitalchina.common.GatewayRequestCallback;
import com.digitalchina.common.GatewayResponseExecutor;
import com.digitalchina.common.OrderStatusEnum;
import com.digitalchina.common.RtnData;
import com.digitalchina.common.service.IFreePayService;
import com.digitalchina.common.service.IOrderService;
import com.digitalchina.common.service.IPayService;
import com.digitalchina.common.util.*;
import com.digitalchina.config.CallUrlConfig;
import com.digitalchina.dao.IdGeneratorDao;
import com.digitalchina.dao.OrderDetailMapper;
import com.digitalchina.dao.OrderInfoMapper;
import com.digitalchina.dao.RouterRuleMapper;
import com.digitalchina.domain.OrderDetail;
import com.digitalchina.domain.OrderInfo;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.*;
import com.digitalchina.exception.PlaceOrderException;
import com.digitalchina.exception.ServiceRuntimeException;
import com.digitalchina.resttemplate.httpclient.RestTemplateWithHttpClientUtil;
import com.digitalchina.resttemplate.ribbon.retryable.RetryableLoadbalancedRestTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.jmx.support.ObjectNameManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Snail on 2016/12/1.
 */
@Service
public class IOrderServiceImpl implements IOrderService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private IFreePayService iFreePayService;

    @Autowired
    private IPayService payService;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    UserService userService;

    @Autowired
    RouterRuleMapper routerRuleMapper;

    @Autowired
    IdGeneratorDao idGeneratorDao;
    @Autowired
    private ISourceServieImpl iSourceService;

    @Autowired
    DoFreeOrderServiceImpl doFreeOrderService;

    @Autowired
    DoPayOrderServiceImpl doPayOrderService;

    @Autowired
    CallUrlConfig callUrlConfig;

    @Autowired
    RetryableLoadbalancedRestTemplateUtil restTemplateUtil;

    @Autowired
    IDataPurchaseOrNotService iDataPurchaseOrNotService;

    @Autowired
    IApiPurchaseOrNotService iApiPurchaseOrNotService;

    @Autowired
    IAppPurchaseOrNotService iAppPurchaseOrNotService;

    @Autowired
    OrderDetailServiceImpl orderDetailService;
    @Autowired
    RestTemplateWithHttpClientUtil httpClientUtil;

    private static final Logger logger = LoggerFactory.getLogger(IOrderServiceImpl.class);


    @Override
    public List<OrderInfo> getOrderListByUserId(String userId) {

        return null;
    }

    /**
     * 下单接口
     *
     * @param odto
     * @return
     */
    @Override
    @Transactional
    public void placeOrder(OmitOrderDto odto, UserData userData) { //数据在有效期内没有必要在下单
 //  public void placeOrder(OmitOrderDto odto, HttpServletRequest request, HttpServletResponse response) { //数据在有效期内没有必要在下单
            if (odto == null) throw new PlaceOrderException(ChangeConstants.ERROR_CODE,"传入订单参数不能为空");
            List<SourcePackage> sourcePackages = new ArrayList<>();
            List<WeiAppPackage> weiAppPackages = new ArrayList<>();
            List<Integer> sourceLists = new ArrayList<>();
            Map<String, PackageDto> packageDtoMap = null;
            Map<String, WeiAppDto> weiAppDtoMap = null;
            Map<Integer, SourceDto> sourceDtoMap = null;
            //获取该批资源的详细信息
            for (OmitSourceDto osdto : odto.getSourceList()) {
                if (ChangeConstants.API_TYPE.equals(osdto.getSourceType())) {
                    SourcePackage sourcePackage = new SourcePackage();
                    sourcePackage.setChargeRuleId(osdto.getChar_rule_id());
                    sourcePackage.setApiServiceId(osdto.getSourceId());
                    sourcePackages.add(sourcePackage);
                } else if (ChangeConstants.DATA_TYPE.equals(osdto.getSourceType())) {
                    sourceLists.add(osdto.getSourceId());
                }else if(ChangeConstants.WAI_APP_TYPE.equals(osdto.getSourceType())){
                    WeiAppPackage weiAppPackage = new WeiAppPackage();
                    weiAppPackage.setChargeRuleId(osdto.getChar_rule_id());
                    weiAppPackage.setAppId(osdto.getSourceId());
                    weiAppPackages.add(weiAppPackage);
                }
            }
            if (sourcePackages != null && sourcePackages.size() > 0) {
                packageDtoMap = iSourceService.getSourcePackageMap(sourcePackages, userData);
            }
            if (sourceLists != null && sourceLists.size() > 0) {
                sourceDtoMap = iSourceService.getSourceMap(sourceLists);
            }
            if (weiAppPackages != null && weiAppPackages.size() > 0) {
                weiAppDtoMap = iSourceService.getWeiAppPackageMap(weiAppPackages,userData);
            }
            boolean isfree = false;//判断是否免费
            for (OmitSourceDto osdto : odto.getSourceList()) {
                PackageDto pdto = null;
                SourceDto sdto = null;
                WeiAppDto wdto =null;
                //如果为api资源
                if (ChangeConstants.API_TYPE.equals(osdto.getSourceType())) {
                    pdto = packageDtoMap.get(osdto.getSourceId() + "" + osdto.getChar_rule_id());
                    isfree = pdto.isFree();

                } else if (ChangeConstants.DATA_TYPE.equals(osdto.getSourceType())) { //数据资源
                    sdto = sourceDtoMap.get(osdto.getSourceId());
                    isfree = sdto.isFree();
                }else if(ChangeConstants.WAI_APP_TYPE.equals(osdto.getSourceType())){
                    wdto = weiAppDtoMap.get(osdto.getSourceId() + "" + osdto.getChar_rule_id());
                    isfree = wdto.isFree();
                }
            }

            if (isfree && odto.getSourceList().size()>1) {
                throw new PlaceOrderException(ChangeConstants.ERROR_CODE,"下单失败,免费单独下单");
            } else if(isfree){
                doFreeOrderService.doFreeOrder(odto, packageDtoMap, sourceDtoMap,weiAppDtoMap, userData);
            }else{ doPayOrderService.doPayOrder(odto, packageDtoMap, sourceDtoMap,weiAppDtoMap ,userData);}
    }

    @Override
    public void updateOrderInfo(OrderInfo orderInfo) {

        orderInfoMapper.updateOrderInfo(orderInfo);
    }

    @Override
    public List<OrderDetail> getOrderListByOrderNum(String orderNum) {
        return orderDetailMapper.getOrderDetailByOrderNum(orderNum);
    }

    @Override
    public OrderInfo getOrderInfoByOrderNum(String orderNum) {
        return orderInfoMapper.findByOrderNum(orderNum);
    }

    @Override
    public List<OrderInfoDto> getOrderInfoListByUser(Map<String, Object> confMap) {
        List<OrderInfoDto>  orderInfos = new ArrayList<>();
        List<OrderInfo> orderInfoList = orderInfoMapper.getOrderInfoListByUser(confMap);
        for(OrderInfo orderInfo:orderInfoList){
            OrderInfoDto orderInfoDto= new OrderInfoDto();
            orderInfoDto.setOrderNum(orderInfo.getOrderNum());
            orderInfoDto.setOrderTime(DateUtil.format(orderInfo.getOrderTime()));
            orderInfoDto.setOrderStatus(OrderStatusEnum.getStatus(orderInfo.getOrderStatus()));
            Map<String,Object> conf = new HashMap<>();
            conf.put("userId",confMap.get("userId"));
            conf.put("orderNum",orderInfo.getOrderNum());
            List<OrderDetail> orderDetails = orderDetailMapper.getByUserIdAndOrderNum(conf);
            for(OrderDetail orderDetail:orderDetails){
                JSONObject parse = (JSONObject) JSONObject.parse( orderDetail.getSourceJson());
                if(orderDetail.isFree()){
                    orderDetail.setDefaulTime(-1);
                }else{
                    if(ChangeConstants.API_TYPE.equals(orderDetail.getResourceType())){
                        if(ChangeConstants.CHARGE_TYPE_BY_TIME.equals(parse.getString("chargeMethod"))){
                            if(parse.getString("monthLimit")!=null){
                                orderDetail.setDefaulTime(parse.getInteger("monthLimit")*orderDetail.getItemNumber());
                            }else{
                                orderDetail.setDefaulTime(-1);
                            }
                        }if(ChangeConstants.CHARGE_TYPE_BY_FREQUENCY.equals(parse.getString("chargeMethod"))){
                            if(parse.getString("chargeCount")!=null){
                                orderDetail.setDefaulTime(parse.getInteger("chargeCount")*orderDetail.getItemNumber());
                            }else{
                                orderDetail.setDefaulTime(-1);
                            }
                        }
                    }else if(ChangeConstants.WAI_APP_TYPE.equals(orderDetail.getResourceType())){
                        if(ChangeConstants.CHARGE_TYPE_BY_TIME.equals(parse.getString("chargeMethod"))){
                            if(parse.getString("invokeLimit")!=null){
                                orderDetail.setDefaulTime(parse.getInteger("invokeLimit")*orderDetail.getItemNumber());
                            }else{
                                orderDetail.setDefaulTime(-1);
                            }
                        }if(ChangeConstants.CHARGE_TYPE_BY_FREQUENCY.equals(parse.getString("chargeMethod"))){
                            if(parse.getString("chargeCount")!=null){
                                orderDetail.setDefaulTime(parse.getInteger("chargeCount")*orderDetail.getItemNumber());
                            }else{
                                orderDetail.setDefaulTime(-1);
                            }
                        }

                    }
                    else{
                        orderDetail.setDefaulTime(-1);
                    }
                }
            }
            orderInfoDto.setSourceDetail(orderDetails);
            orderInfos.add(orderInfoDto);
        }
        return orderInfos;
    }

    @Override
    public int getOrderListCount(Map<String, Object> usrInfoMap) {
        return orderInfoMapper.getSelfOrderListCount(usrInfoMap);
    }

    /**
     * 判断是否购买
     *
     * @param osdto
     * @param userData
     * @return
     */
    @Override
    public RtnData havePurchase(OmitSourceDto osdto, UserData userData) {
        IsFreePurchaseDto isFreePurchaseDto = this.isFreePurchase(osdto,userData);
        if (ChangeConstants.DATA_TYPE.equals(osdto.getSourceType())) {
            return iDataPurchaseOrNotService.havePurchase(osdto,userData);
        }
        else if(ChangeConstants.API_TYPE.equals(osdto.getSourceType())){
            return  iApiPurchaseOrNotService.havePurchase(osdto,userData);
        }else{
            return  iAppPurchaseOrNotService.havePurchase(osdto,userData);
        }
    }

    @Override
    public IsFreePurchaseDto isFreePurchase(OmitSourceDto osdto,UserData userData){
        Map<String, Object> confMap = new HashMap<>();
        confMap.put("userId", userData.getUserId());
        confMap.put("sourceId", osdto.getSourceId());
        confMap.put("nowDate", new Date());
        confMap.put("sourceType", osdto.getSourceType());
        IsFreePurchaseDto ifFreeDto = routerRuleMapper.getIfFreeDto(confMap);
        return  ifFreeDto;
    }



    /*
    获取我的当前消费的金额
     */
    @Override
    public String getMyConsumeCash(UserData userData) {
        Map<String ,Object>  confMap = new HashMap<>();
        confMap.put("userId",userData.getUserId());
        confMap.put("orderStatus",OrderStatusEnum.PAY_SUCCESS.getIndex());
        double totalCash =0;
        if(orderInfoMapper.getUserConsumeCash(confMap)==null) return totalCash+"";
        else totalCash = (double)orderInfoMapper.getUserConsumeCash(confMap);
        return totalCash+"";
    }

    //获取 订单资源详情
    @Override
    public OrderInfoDto getOrderDetail(String userId, String orderNum) {
        Map<String,Object> orderMap = new HashMap<>();
        orderMap.put("userId",userId);
        orderMap.put("orderNum",orderNum);
        OrderInfo orderInfo = orderInfoMapper.findByOrderNum(orderNum);
        OrderInfoDto orderInfoDto = new OrderInfoDto();
        if(orderInfo!=null){
            orderInfoDto.setOrderCash(orderInfo.getOrderCashTotal());
            orderInfoDto.setOrderNum(orderNum);
            orderInfoDto.setOrderStatus(orderInfo.getOrderStatus()+"");
            orderInfoDto.setOrderUserName(orderInfo.getOrderUserName());
            orderInfoDto.setPayWay(orderInfo.getPayType());
            List<OrderDetail> orderDetails = orderDetailMapper.getByUserIdAndOrderNum(orderMap);
            orderInfoDto.setSourceDetail(orderDetails);
        }
        return orderInfoDto;
    }

    @Override
    public List<SourceIdCountDto> getCountSource(){
        List<SourceIdCountDto> sourceIdCountDtos = orderDetailMapper.getCountSource();
        return sourceIdCountDtos;
    }

}
