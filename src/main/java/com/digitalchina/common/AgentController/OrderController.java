package com.digitalchina.common.AgentController;

import com.digitalchina.common.RtnData;
import com.digitalchina.common.service.IOrderService;
import com.digitalchina.common.service.Impl.GenerateService;
import com.digitalchina.common.util.ChangeConstants;
import com.digitalchina.common.util.DateUtil;
import com.digitalchina.common.util.HttpHeaderInfoUtil;
import com.digitalchina.dao.OrderDetailMapper;
import com.digitalchina.domain.OrderInfo;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.*;
import com.digitalchina.pagination.Page;
import com.digitalchina.pagination.PaginationUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Snail on 2016/11/29.
 */
@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	IOrderService iorderService;

	@Autowired
	GenerateService generateService;

	@Autowired
	OrderDetailMapper orderDetailMapper;
	/**
	 * 用户下单
	 * 
	 * @param
	 * @return
	 */
	@ApiOperation(value = "用户下单",
			notes = "header 中需要包含mscx-area-code, 用于指明获取哪个区域平台的数据 ",
			httpMethod = "POST",
			produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "mscx-area-code", value = "区域编码", dataType = "String", defaultValue = "440100", paramType = "header", required = true),
			@ApiImplicitParam(name = "mscx-user-data", value = "用户信息", dataType = "String", defaultValue = "{\"userId\":\"440100000002328\",\"account\":\"wenhk001\",\"mobile\":\"13899992222\",\"name\":\"xxxx\"}", paramType = "header", required = true),
			@ApiImplicitParam(name = "oodto", dataType = "OmitOrderDto", paramType = "body")
	})
	@RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
	public RtnData doOrder(@RequestBody OmitOrderDto oodto, HttpServletRequest request, HttpServletResponse response){
		UserData userData = HttpHeaderInfoUtil.getUserData(request);
		System.out.print("..............."+request.getHeader(ChangeConstants.HEADER_USERDATA));
		userData.setArea(HttpHeaderInfoUtil.getAreaCode(request));
		System.out.print("**************"+userData.getAccount()+"00000000000000000000");
		String orderNum =generateService.getOrderNum(HttpHeaderInfoUtil.getAreaCode(request));
		userData.setTemNum(orderNum);
		iorderService.placeOrder(oodto,userData);
		return  new RtnData("000000","下单成功","OK",orderNum);
	}

	/**
	 * 获取当前用户订单
	 * 
	 * @param
	 * @return
	 */
	//获取当前用户购买的数据订单
	@ApiOperation(value = "获取当前用户订单",
			httpMethod = "GET",
			produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "mscx-area-code", value = "区域编码", dataType = "String", defaultValue = "440100", paramType = "header", required = true),
			@ApiImplicitParam(name = "mscx-user-data", value = "用户信息", dataType = "String", defaultValue = "{\"userId\":\"440100000002328\",\"account\":\"wenhk001\",\"mobile\":\"13899992222\",\"name\":\"xxxx\"}", paramType = "header", required = true),
			@ApiImplicitParam(name = "page", value = "查询的页码", dataType = "int", defaultValue = "0", paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页显示数量", dataType = "int", defaultValue = "10", paramType = "query")
	})
	@RequestMapping(value = "/getOrderList", method = RequestMethod.GET)
	public RtnData getOrderList(
			@RequestParam(required = false,defaultValue = "10") Integer pageSize,
			@RequestParam(required = false,defaultValue = "1") Integer page,HttpServletRequest request){
		UserData userData = HttpHeaderInfoUtil.getUserData(request);
		String area = HttpHeaderInfoUtil.getAreaCode(request);
		String userId = userData.getUserId();
		Map<String,Object> usrInfoMap = new HashMap<>();
		usrInfoMap.put("userId",userId);
		usrInfoMap.put("area",area);
		//  usrInfoMap.put("orderStatus", OrderStatusEnum.PAY_SUCCESS.getIndex());
		int count = iorderService.getOrderListCount(usrInfoMap);
		Page pagination = PaginationUtils.getPageParam(count, pageSize, page);
		usrInfoMap.put("startIndex", pagination.getStartIndex());
		usrInfoMap.put("endIndex", pagination.getEndIndex());
		pagination.setUrl(request.getRequestURI());//计算出分页查询时需要使用的索引
		Map<String, Object> result = new HashMap<String, Object>(4,1.0f);
		result.put("list",iorderService.getOrderInfoListByUser(usrInfoMap));
		result.put("page",pagination);
		return new RtnData(result);
	}


	/**
	 * 获取当前用户的消费金额
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getMyConsumeCash", method = RequestMethod.GET)
	@ResponseBody
	public RtnData getMyConsumeCash(HttpServletRequest request){
        UserData userData = HttpHeaderInfoUtil.getUserData(request);
		String total = iorderService.getMyConsumeCash(userData);
		return new RtnData("000000","success","OK",total);
	}


	/**
	 * 更改订单状态
	 * 
	 * @param orderStatus
	 * @return
	 */
	@RequestMapping(value = "/changeOrderStatus", method = RequestMethod.PUT)
	public RtnData changeOrderStatus(@RequestParam Integer orderStatus,
			@RequestParam Long orderNum) {
		return new RtnData();
	}

	//判断是否购买
	@ApiOperation(value = "是否购买",
			notes = "header 中需要包含mscx-area-code, 用于指明获取哪个区域平台的数据 ",
			httpMethod = "GET",
			produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sourceId", value = "资源id", dataType = "String", paramType = "query", required = true),
			@ApiImplicitParam(name = "char_rule_id", value = "套餐id", dataType = "int",  paramType = "query"),
			@ApiImplicitParam(name = "sourceType", value = "资源类型", dataType = "String",  paramType = "query")

	})
	@RequestMapping(value = "/purchaseOrNot", method = RequestMethod.GET)
	public RtnData havePurchase(@RequestParam(required = false)  String sourceId,
								@RequestParam(required = false)  Integer char_rule_id,
								@RequestParam(required = false) String sourceType,HttpServletRequest request) {
		if(StringUtils.isEmpty(sourceId)||StringUtils.isEmpty(char_rule_id)||StringUtils.isEmpty(sourceType)){
			return new RtnData("9999","不能为空","error",null);
		}
		UserData userData = HttpHeaderInfoUtil.getUserData(request);
		OmitSourceDto omitSourceDto = new OmitSourceDto();
		omitSourceDto.setSourceId(Integer.parseInt(sourceId));
		omitSourceDto.setChar_rule_id(char_rule_id);
		omitSourceDto.setSourceType(sourceType);
		return iorderService.havePurchase(omitSourceDto,userData);
	}

	//根据用户 以及订单号 获取订单的详情

	@RequestMapping(value = "/getOrderDetail", method = RequestMethod.GET)
	@ResponseBody
	public OrderInfoDto getOrderDetail(
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true ) String  orderNum){
			UserData userData = HttpHeaderInfoUtil.getUserData(request);
			return iorderService.getOrderDetail(userData.getUserId(),orderNum);
	}

	/**
	 * 根据用户ids 获取订单信息
	 * @param dataAndIdsDto
	 * @return
	 */
	@RequestMapping(value = "/getOrderByIds", method = RequestMethod.POST)
	@ResponseBody
	public RtnData getOrderInfo(@RequestBody DataAndIdsDto dataAndIdsDto){
		Map<String,Object> conf = new HashMap<>();
		conf.put("userIds",dataAndIdsDto.getUserIds());
		conf.put("startTime",DateUtil.format(dataAndIdsDto.getQueryDate()+" 00:00:00"));
		conf.put("endTime", DateUtil.format(dataAndIdsDto.getQueryDate()+" 23:59:59"));
		List<Map> orderInfoList = orderDetailMapper.getOrderDetailByUserIds(conf);
		return RtnData.ok(orderInfoList,"success");
	}
}




