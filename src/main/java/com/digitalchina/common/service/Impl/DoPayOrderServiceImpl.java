package com.digitalchina.common.service.Impl;

import com.digitalchina.common.OrderStatusEnum;
import com.digitalchina.common.RtnData;
import com.digitalchina.common.service.IFreePayService;
import com.digitalchina.common.service.IPayService;
import com.digitalchina.common.util.ChangeConstants;
import com.digitalchina.common.util.DateUtil;
import com.digitalchina.common.util.HttpHeaderInfoUtil;
import com.digitalchina.dao.IdGeneratorDao;
import com.digitalchina.dao.OrderDetailMapper;
import com.digitalchina.dao.OrderInfoMapper;
import com.digitalchina.dao.RouterRuleMapper;
import com.digitalchina.domain.OrderInfo;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.*;
import com.digitalchina.exception.PlaceOrderException;
import com.mysql.jdbc.PacketTooBigException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Snail on 2016/12/15.
 */
@Service
public class DoPayOrderServiceImpl {


    @Autowired
    IdGeneratorDao idGeneratorDao;

    @Autowired
    OrderDetailServiceImpl orderDetailService;

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

//    private String getOrderNum(String areaId){
//        idGeneratorDao.increase();
//        int serial = idGeneratorDao.current();
//        String nineTime = DateUtil.getNineTime();
//        String orderNum = areaId+nineTime+serial;
//        return orderNum;
//    }

    /**
     * 收费下单
     * @param odto
     * @param packageDtoMap
     * @param sourceDtoMap
     * @param userData
     * @return
     */
    @Transactional
    void doPayOrder(OmitOrderDto odto, Map<String,PackageDto> packageDtoMap, Map<Integer,SourceDto> sourceDtoMap, Map<String, WeiAppDto> weiAppDtoMap, UserData userData){
        //创建订单
        String name = userData.getAccount();
        String userId = userData.getUserId();
        Date orderDate = new Date();
        OrderInfo orderInfo = new OrderInfo();
        String orderNum =userData.getTemNum();
        orderInfo.setArea(userData.getArea());
        orderInfo.setOrderNum(orderNum);
        orderInfo.setUserId(userId);
        orderInfo.setOrderTime(orderDate);
        orderInfo.setCreatedTime(orderDate);
        orderInfo.setOrderUserName(name);
        orderInfo.setUpdatedBy(userData.getAccount());
        orderInfo.setUdpatedTime(orderDate);
        orderInfo.setOrderType("online");
        orderInfo.setOrderStatus(OrderStatusEnum.NOT_PAY.getIndex());
        double total_cash=0.0;
        for(OmitSourceDto osdto :odto.getSourceList()){
            if (osdto.getSourceType().equals(ChangeConstants.API_TYPE)) {
                PackageDto pdto = packageDtoMap.get(osdto.getSourceId()+""+osdto.getChar_rule_id());
                total_cash+=pdto.getPackagePrice()*osdto.getItem_num();

            } else if ( osdto.getSourceType().equals( ChangeConstants.DATA_TYPE)) { //数据资源
                SourceDto sdto = sourceDtoMap.get(osdto.getSourceId());
                if(osdto.getItem_num()>1) throw new PlaceOrderException("999999","同一数据资源一次只能买一次");
                total_cash+=sdto.getSourcePrice()*osdto.getItem_num();
            }else if(osdto.getSourceType().equals(ChangeConstants.WAI_APP_TYPE)){
                WeiAppDto wdto = weiAppDtoMap.get(osdto.getSourceId()+""+osdto.getChar_rule_id());
                total_cash+=wdto.getSourcePrice()*osdto.getItem_num();
            }
        }

        for(OmitSourceDto osdto :odto.getSourceList()) {
            PackageDto pdto = null;
            SourceDto sdto = null;
            WeiAppDto wdto = null;
            //如果为api资源
            if (osdto.getSourceType().equals(ChangeConstants.API_TYPE)) {
                pdto = packageDtoMap.get(osdto.getSourceId() + "" + osdto.getChar_rule_id());

            } else if (osdto.getSourceType().equals(ChangeConstants.DATA_TYPE)) { //数据资源
                sdto = sourceDtoMap.get(osdto.getSourceId());
            }else if(osdto.getSourceType().equals(ChangeConstants.WAI_APP_TYPE)){
                wdto = weiAppDtoMap.get(osdto.getSourceId() + "" + osdto.getChar_rule_id());
            }
            if((sdto !=null && sdto.getSourcePrice()==0 )||(pdto!=null&& pdto.getPackagePrice()==0)||(wdto!=null && wdto.getSourcePrice()==0)){
                orderInfo.setOrderStatus(OrderStatusEnum.PAY_SUCCESS.getIndex());
            }
        }
        orderInfo.setOrderCashTotal(total_cash);
        orderInfoMapper.insert(orderInfo);
        int id  = orderInfo.getId();
            for(OmitSourceDto osdto :odto.getSourceList()) {
                PackageDto pdto = null;
                SourceDto sdto = null;
                WeiAppDto wdto=null;
                //如果为api资源
                if (osdto.getSourceType().equals(ChangeConstants.API_TYPE)) {
                    pdto = packageDtoMap.get(osdto.getSourceId() + "" + osdto.getChar_rule_id());

                } else if (osdto.getSourceType().equals(ChangeConstants.DATA_TYPE)) { //数据资源
                    sdto = sourceDtoMap.get(osdto.getSourceId());
                }else if(osdto.getSourceType().equals(ChangeConstants.WAI_APP_TYPE)) {
                    wdto = weiAppDtoMap.get(osdto.getSourceId() + "" + osdto.getChar_rule_id());
                    if(!(ChangeConstants.ONLINE_OR_NOT.equals(wdto.getStatus()))){
                        throw new PlaceOrderException(ChangeConstants.ERROR_CODE, pdto.getPackageName() + "该资源已经下架");}
                    if (ChangeConstants.CHARGE_STATUS_OUTLINE.equals(wdto.getChargeStatus())) {
                        throw new PlaceOrderException(ChangeConstants.ERROR_CODE, pdto.getPackageName() + "该资源已经下架");
                    }
                }
                //判断收费的0元套餐用户已经拥有
                if((sdto !=null && sdto.getSourcePrice()==0 )||(pdto!=null&& pdto.getPackagePrice()==0)||(wdto!=null && wdto.getSourcePrice()==0)){
                    Map<String,Object> sourceMp =  new HashMap<String ,Object>();
                    sourceMp.put("sourceId",osdto.getSourceId());
                    sourceMp.put("packageId",osdto.getChar_rule_id());
                    sourceMp.put("userId", userData.getUserId());
                    sourceMp.put("area",userData.getArea());
                    int sourceOrderNum =orderDetailMapper.countBySourcdIdOrPackId(sourceMp);
                    if(sourceOrderNum==0){
                        iFreePayService.doDetailOrder(osdto,orderNum,sdto,pdto,wdto,userData,id);
                    }else throw new PlaceOrderException("该资源已经下单");
                }else{
                    //如果收费数据在有效期内 则不允许在此下单
                    if(osdto.getSourceType().equals(ChangeConstants.DATA_TYPE)){  //数据资源
                        Map<String,Object> confMap = new HashMap<>();
                        confMap.put("userId",userId);
                        confMap.put("sourceId",sdto.getSourceId());
                        confMap.put("nowDate",new Date());
                        confMap.put("sourceType",ChangeConstants.DATA_TYPE);
                        Integer count =  routerRuleMapper.getByAllId(confMap);
                        if(count ==null || count==0){
                            payService.doDetailOrder(osdto,orderNum,sdto,pdto,wdto,userData,id);
                        }else throw new PlaceOrderException("该资源已经下单");
                    }else{
                        //套餐是 按次 按时来累加的 是可以过来创建订单的
                        if(pdto!=null){
                            if(pdto.getCountLimit()<0 || pdto.getCountLimit()==null){
                                payService.doDetailOrder(osdto,orderNum,sdto,pdto,wdto,userData,id);
                            } else{ //判断是否超购限购次数
                                Map<String,Object> conf = new HashMap<>();
                                conf.put("userId",userData.getUserId());
                                conf.put("sourceId",pdto.getSourceId());
                                conf.put("packageId",pdto.getPackageId());
                                conf.put("payStatus", OrderStatusEnum.PAY_SUCCESS.getIndex());
                                conf.put("resourceType", ChangeConstants.API_TYPE);
                                int countPay =  orderDetailService.getSelfApiChargeCount(conf);
                                if(countPay<pdto.getCountLimit()){
                                    payService.doDetailOrder(osdto,orderNum,sdto,pdto,wdto,userData,id);
                                }else throw new PlaceOrderException("超过限购次数");
                            }
                        }
                        if(wdto!=null){
                            if(wdto.getCountLimit()<0 || wdto.getCountLimit()==null){
                                payService.doDetailOrder(osdto,orderNum,sdto,pdto,wdto,userData,id);
                            }else{
                                Map<String,Object> conf = new HashMap<>();
                                conf.put("userId",userData.getUserId());
                                conf.put("sourceId",wdto.getSourceId());
                                conf.put("packageId",wdto.getPackageId());
                                conf.put("payStatus", OrderStatusEnum.PAY_SUCCESS.getIndex());
                                conf.put("resourceType", ChangeConstants.WAI_APP_TYPE);
                                int countPay =  orderDetailService.getSelfApiChargeCount(conf);
                                if(countPay<wdto.getCountLimit()){
                                    payService.doDetailOrder(osdto,orderNum,sdto,pdto,wdto,userData,id);
                                }else throw new PlaceOrderException("超过限购次数");
                            }
                        }

                    }
                }
            }
    }
}
