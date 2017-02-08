package com.digitalchina.common.AgentController;

import com.digitalchina.common.RtnData;
import com.digitalchina.common.service.ApiOfflineMeetService;
import com.digitalchina.common.util.Constants;
import com.digitalchina.common.util.HttpHeaderInfoUtil;
import com.digitalchina.domain.ApiOfflineMeet;
import com.digitalchina.domain.UserData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 *
 */
@RestController
public class ApiOfflineMeetController {

	@Autowired
    private ApiOfflineMeetService apiOfflineMeetService;

	/**
	 * Save the information for offline mode
	 * @param apiServiceId
	 * @param resReq
	 * @param purpose
	 * @param contact
	 * @param contactNo
	 * @param type
	 * @param cname
	 * @return
	 */
	@ApiOperation(value = "申请线下洽谈",
			notes = "header 中需要包含mscx-user-data,用户信息",
			httpMethod = "GET",
			produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "apiServiceId",  value = "api服务id",  dataType = "Integer", defaultValue = "1001", paramType = "query"),
			@ApiImplicitParam(name = "resReq",  value = "资源需求",  dataType = "String", defaultValue = "资源需求001", paramType = "query"),
			@ApiImplicitParam(name = "purpose",  value = "使用意向",  dataType = "String", defaultValue = "使用意向001", paramType = "query"),
			@ApiImplicitParam(name = "contact",  value = "联系人",  dataType = "String", defaultValue = "张xx", paramType = "query"),
			@ApiImplicitParam(name = "contactNo",  value = "联系电话",  dataType = "String", defaultValue = "15190002885", paramType = "query"),
			@ApiImplicitParam(name = "type",  value = "资源类型",  dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "cname",  value = "资源名称",  dataType = "String", paramType = "query"),

	})
	@RequestMapping(value = "/service/apiOfflineMeet.do", method = RequestMethod.GET)
    public RtnData insert(HttpServletRequest request,
                          @RequestParam(required = true) int apiServiceId,
                          @RequestParam(required = true) String resReq,
                          @RequestParam(required = true) String purpose,
                          @RequestParam(required = true) String contact,
                          @RequestParam(required = true) String contactNo,
						  @RequestParam(required = true) String type,
						  @RequestParam(required = true) String cname)
	{
		UserData userData = HttpHeaderInfoUtil.getUserData(request);
    	ApiOfflineMeet apiOfflineMeet = new ApiOfflineMeet();
		apiOfflineMeet.setUserId(userData.getUserId());
		apiOfflineMeet.setServiceId(apiServiceId);
		apiOfflineMeet.setResReq(resReq);
		apiOfflineMeet.setPurpose(purpose);
		apiOfflineMeet.setContact(contact);
		apiOfflineMeet.setContactNo(contactNo);
		String userName = "";
		if(StringUtils.isEmpty(userData.getName())){
			userName = userData.getAccount();
		}else{
			userName = userData.getName();
		}
		apiOfflineMeet.setCreatedBy(userName);
		apiOfflineMeet.setCreatedTime(new Date());
		apiOfflineMeet.setCname(cname);
		apiOfflineMeet.setType(type);
		apiOfflineMeet.setProcessFlag(Constants.OFFLINE_MEET_ACCEPT);

        int result = apiOfflineMeetService.insert(apiOfflineMeet);
        return RtnData.ok(result,"添加成功");
    }
    
}
