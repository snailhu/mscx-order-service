package com.digitalchina.common.AgentController;

import com.digitalchina.common.OrderStatusEnum;
import com.digitalchina.common.RtnData;
import com.digitalchina.common.service.Impl.DataServiceImpl;
import com.digitalchina.common.util.ChangeConstants;
import com.digitalchina.common.util.DateUtil;
import com.digitalchina.common.util.HttpHeaderInfoUtil;
import com.digitalchina.domain.OrderDetail;
import com.digitalchina.domain.PayLog;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.DownLoadDataDto;
import com.digitalchina.dto.OrderApiDto;
import com.digitalchina.dto.ResultDto;
import com.digitalchina.pagination.Page;
import com.digitalchina.pagination.PaginationUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Snail on 2016/12/6.
 */

@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    DataServiceImpl dataService;
    //获取当前用户购买的数据订单
    @ApiOperation(value = "获取当前用户购买的数据订单",
            httpMethod = "GET",
            produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mscx-area-code", value = "区域编码", dataType = "String", defaultValue = "440100", paramType = "header", required = true),
            @ApiImplicitParam(name = "mscx-user-data", value = "用户信息", dataType = "String", defaultValue = "{\"userId\":\"440100000002328\",\"account\":\"wenhk001\",\"mobile\":\"13899992222\",\"name\":\"xxxx\"}", paramType = "header", required = true),
            @ApiImplicitParam(name = "page", value = "查询的页码", dataType = "int", defaultValue = "1", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量", dataType = "int", defaultValue = "10", paramType = "query")
    })

    @RequestMapping(value = "/getSelfDataList", method = RequestMethod.GET)
    @Transactional
    public RtnData getDataList(
            @RequestParam(required = false,defaultValue = "10") Integer pageSize,
            @RequestParam(required = false,defaultValue = "1") Integer page, HttpServletRequest request){
        Map<String,Object> confMap = new HashMap<>();
        UserData userData = HttpHeaderInfoUtil.getUserData(request);
        String name = userData.getName();
        String area = HttpHeaderInfoUtil.getAreaCode(request);
        String userId = userData.getUserId();
        confMap.put("userId",userId);
        confMap.put("resourceType", ChangeConstants.DATA_TYPE);
        int count = dataService.getOrderDataListCount(userId,ChangeConstants.DATA_TYPE, OrderStatusEnum.PAY_SUCCESS.getIndex());
        Page pagination = PaginationUtils.getPageParam(count, pageSize, page);
        confMap.put("startIndex", pagination.getStartIndex());
        confMap.put("endIndex", pagination.getEndIndex());
        List<DownLoadDataDto> downLoadDataDtos =dataService.getByUserIdAndSourceType(confMap);
        Map<String, Object> result = new HashMap<String, Object>(4,1.0f);
        result.put("list",downLoadDataDtos);
        result.put("page",pagination);
        return new RtnData(result);
    }

    //获取资源统计

    @RequestMapping(value = "/getStatistics", method = RequestMethod.GET)
    public RtnData getDataDetailForInternal(@RequestParam String dataIds, @RequestParam String resourceType) {
        List<Map> dataInfos = dataService.getSourceStatistics(dataIds,resourceType);
        return new RtnData(dataInfos);
    }

    //根据时间获取资源详情

    @RequestMapping(value = "/getListByTime", method = RequestMethod.GET)
    RtnData getOrderDetailByTime(@RequestParam(required = false) String startTime){
        Date startDate=null;
        if(startTime!=null){
            startDate = DateUtil.format(startTime);
        }
        List<OrderDetail> orderDetails= dataService.getOrderDetailByTime(startDate, new Date());
        return new RtnData(orderDetails);
    }
}
