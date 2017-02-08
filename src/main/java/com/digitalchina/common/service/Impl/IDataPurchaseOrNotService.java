package com.digitalchina.common.service.Impl;

import com.digitalchina.common.RtnData;
import com.digitalchina.common.service.IOrderService;
import com.digitalchina.common.util.ChangeConstants;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.IsFreePurchaseDto;
import com.digitalchina.dto.OmitSourceDto;
import com.digitalchina.dto.SourceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Snail on 2017/1/12.
 */
@Service
public class IDataPurchaseOrNotService {

    @Autowired
    IOrderService iOrderService;

    @Autowired
    ISourceServieImpl iSourceServie;

    public RtnData havePurchase(OmitSourceDto osdto, UserData userData) {
        IsFreePurchaseDto isFreePurchaseDto = iOrderService.isFreePurchase(osdto,userData);
        if(isFreePurchaseDto==null) return new RtnData(ChangeConstants.SUCCESS_CODE, "可以订购", "OK",ChangeConstants.PERMISSION_ORDER);
        List<Integer> dataId = new ArrayList<>();
        dataId.add(osdto.getSourceId());
        Map<Integer, SourceDto> sourceDtoMap = iSourceServie.getSourceMap(dataId);
        SourceDto sourceDto =sourceDtoMap.get(osdto.getSourceId());
        if(sourceDto.isFree()){
            if(isFreePurchaseDto.getCountNum() > 0){
                return new RtnData(ChangeConstants.SUCCESS_CODE, "已经购买", "OK", ChangeConstants.PAY);
            }else return new RtnData(ChangeConstants.SUCCESS_CODE, "可以订购", "OK", ChangeConstants.PERMISSION_ORDER);
        }
        else{
            if (isFreePurchaseDto.getCountNum() > 0 && ChangeConstants.SOURCE_ROUTER_CREATE.equals(isFreePurchaseDto.getSourceStatus())) {
                return new RtnData(ChangeConstants.SUCCESS_CODE, "已经下单请付款", "OK", ChangeConstants.NOT_PAY);
            }
            else if(isFreePurchaseDto.getCountNum() > 0 &&
                    (Integer.parseInt(isFreePurchaseDto.getSourceStatus())>=Integer.parseInt(ChangeConstants.SOURCE_ROUTER_PAY_VERIFY))){
                return new RtnData(ChangeConstants.SUCCESS_CODE, "已经购买", "OK", ChangeConstants.PAY);
            }
            else return new RtnData(ChangeConstants.SUCCESS_CODE, "可以订购", "OK", ChangeConstants.PERMISSION_ORDER);
        }
    }
}
