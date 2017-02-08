package com.digitalchina.common.AgentController;

import com.digitalchina.common.OrderStatusEnum;
import com.digitalchina.common.RtnData;
import com.digitalchina.common.service.Impl.ApiServiceImpl;
import com.digitalchina.common.util.ChangeConstants;
import com.digitalchina.common.util.HttpHeaderInfoUtil;
import com.digitalchina.dao.OrderDetailMapper;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.*;
import com.digitalchina.pagination.Page;
import com.digitalchina.pagination.PaginationUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Snail on 2016/12/6.
 */
@RestController
@RequestMapping("/api")
public class APIController {

    @Autowired
    ApiServiceImpl apiService;

    @Autowired
    OrderDetailMapper orderDetailMapper;

    //获取当前用户 申请购买的API
    @ApiOperation(value = "获取用户自己申请购买的API",
            notes = "header 中需要包含mscx-area-code, 用于指明获取哪个区域平台的数据",
            httpMethod = "GET",
            produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mscx-area-code", value = "区域编码", dataType = "String", defaultValue = "440100", paramType = "header", required = true),
            @ApiImplicitParam(name = "mscx-user-data", value = "用户信息", dataType = "String", defaultValue = "{\"userId\":\"440100000002328\",\"account\":\"wenhk001\",\"mobile\":\"13899992222\",\"name\":\"xxxx\"}", paramType = "header", required = true),
            @ApiImplicitParam(name = "page", value = "查询的页码", dataType = "int", defaultValue = "1", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量", dataType = "int", defaultValue = "10", paramType = "query")
    })
    @RequestMapping(value = "/getSelfApiList", method = RequestMethod.GET)
    public RtnData getDataListT(
                               @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                               @RequestParam(required = false,defaultValue = "1") Integer page,HttpServletRequest request){

        UserData userData = HttpHeaderInfoUtil.getUserData(request);
        String userId = userData.getUserId();
        Map<String,Object>  confMap = new HashMap<>();
        confMap.put("userId",userId);
        confMap.put("resourceType", ChangeConstants.API_TYPE);
        confMap.put("payStatus", OrderStatusEnum.PAY_SUCCESS.getIndex());
        int count = apiService.getSelfApiNum(confMap);
        Page pagination = PaginationUtils.getPageParam(count, pageSize, page);
        confMap.put("startIndex", pagination.getStartIndex());
        confMap.put("endIndex", pagination.getEndIndex());
        List<OrderApiDto> orderApiDtos =apiService.getOrderDetailByUserIdAndType(confMap,userData);
        Map<String, Object> result = new HashMap<String, Object>(4,1.0f);
        result.put("list",orderApiDtos);
        result.put("page",pagination);
        return new RtnData(result);
    }


    //根据api 获取购买的次数(假数据)
    @RequestMapping(value = "/getSelfApiListTt", method = RequestMethod.GET)
    public RtnData getDataList(
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer page,HttpServletRequest request){

        ResultDto<OrderApiDto>  resultDto = new ResultDto<>();
        List<OrderApiDto> orderApiDtos = new ArrayList<>();
        OrderApiDto orderApiDto = new OrderApiDto();

        orderApiDtos.add(orderApiDto);
        Map<String, Object> result = new HashMap<String, Object>(4,1.0f);
        result.put("list",orderApiDtos);
        result.put("page",new Page());
        resultDto.getResultDtoLists();
        return new RtnData(resultDto);
    }

//自己的api总数（zhen）
@ApiOperation(value = "获取用户自己申请购买的API总数",
        notes = "header 中需要包含mscx-area-code, 用于指明获取哪个区域平台的数据"+ "\"result\":{\"totalApi\":30}",
        httpMethod = "GET",
        produces = "application/json")
@ApiImplicitParams({
        @ApiImplicitParam(name = "mscx-area-code", value = "区域编码", dataType = "String", defaultValue = "440100", paramType = "header", required = true),
        @ApiImplicitParam(name = "mscx-user-data", value = "用户信息", dataType = "String", defaultValue = "{\"userId\":\"440100000002328\",\"account\":\"wenhk001\",\"mobile\":\"13899992222\",\"name\":\"xxxx\"}", paramType = "header", required = true),
    })
    @RequestMapping(value = "/getSelfApiCount", method = RequestMethod.GET)
    public RtnData getDataListT(HttpServletRequest request){
        UserData userData = HttpHeaderInfoUtil.getUserData(request);
        Map<String,Object>  confMap = new HashMap<>();
        confMap.put("userId",userData.getUserId());
        confMap.put("resourceType", ChangeConstants.API_TYPE);
        int count = apiService.getSelfApiNum(confMap);
        return new RtnData("000000","success","OK",count);
    }



}
