package com.digitalchina.common.service;

import com.digitalchina.domain.UserData;
import com.digitalchina.dto.*;

/**
 * Created by Snail on 2016/12/1.
 */
public interface IFreePayService {
    double doDetailOrder(OmitSourceDto osdto, String orderNum, SourceDto sourceDto, PackageDto packageDto, WeiAppDto wdto, UserData userData, int order_id);
}
