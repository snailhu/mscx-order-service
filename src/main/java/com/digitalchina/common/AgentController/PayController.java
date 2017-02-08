package com.digitalchina.common.AgentController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.digitalchina.common.OrderStatusEnum;
import com.digitalchina.common.RtnData;
import com.digitalchina.common.service.IOrderService;
import com.digitalchina.common.service.IPayLogService;
import com.digitalchina.common.service.IPayService;
import com.digitalchina.common.service.IRouterRuleSeivice;
import com.digitalchina.common.service.Impl.ApiServiceImpl;
import com.digitalchina.common.util.*;
import com.digitalchina.config.CallUrlConfig;
import com.digitalchina.config.PayConfig;
import com.digitalchina.config.StaticsApiAndDataConfig;
import com.digitalchina.dao.OrderDetailMapper;
import com.digitalchina.domain.OrderDetail;
import com.digitalchina.domain.OrderInfo;
import com.digitalchina.domain.RouterRule;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.ApplyDto;
import com.digitalchina.dto.SourceIdCountDto;
import com.digitalchina.resttemplate.httpclient.RestTemplateWithHttpClientUtil;
import com.digitalchina.resttemplate.ribbon.retryable.RetryableLoadbalancedRestTemplateUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Snail on 2016/12/2.
 */
@Controller
@RequestMapping("/order")
public class PayController {

    @Autowired
    IPayService iPayService;

    @Autowired
    ApiServiceImpl apiService;

    @Autowired
    IOrderService iOrderService;

    @Autowired
    IPayLogService iPayLogService;

    @Autowired
    PayConfig payConfig;

    @Autowired
    StaticsApiAndDataConfig staticsApiAndDataConfig;

    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Autowired
    IRouterRuleSeivice iRouterRuleSeivice;

    @Autowired
    CallUrlConfig callUrlConfig;

    @Autowired
    RetryableLoadbalancedRestTemplateUtil restTemplateUtil;

    private static Logger logger = LoggerFactory.getLogger(PayController.class);


    /**
     * 支付接口
     * @param orderNum
     * @param title
     * @param returnUrl
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @ApiOperation(value = "支付接口",
            httpMethod = "GET",
            produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mscx-area-code", value = "区域编码", dataType = "String", defaultValue = "440100", paramType = "header", required = true),
            @ApiImplicitParam(name = "orderNum", value = "订单号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "returnUrl", value = "前端页面回掉地址", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "channel", value = "支付类型", dataType = "String", paramType = "query"),
    })

    @RequestMapping(value = "/payOrder", method = RequestMethod.GET)
    public String payOrder(@RequestParam(required = false) String orderNum,
                           @RequestParam(required = false) String title,
                           @RequestParam(required = false) String returnUrl,
                           @RequestParam(required = false) String channel, HttpServletRequest request, HttpServletResponse response, RedirectAttributes model
    ) {

        //  查寻订单更改状态
        System.out.print("**************************************"+returnUrl+"****************************************************");
        OrderInfo orderInfo = iOrderService.getOrderInfoByOrderNum(orderNum);
        if (orderInfo == null) throw new RuntimeException("订单不存在");
       // orderInfo.setOrderStatus(OrderStatusEnum.PAYING.getIndex());
        orderInfo.setPayType(channel);
        iOrderService.updateOrderInfo(orderInfo);
        double total = orderInfo.getOrderCashTotal();
        int totalMoney = (int) (total * 100);
        String qrPayMode = null;
        String useApp = null;

        //支付渠道(ALI_WEB,ALI_QRCODE,ALI_WAP,WX_NATIVE,UN_WEB,UN_WAP)
        Map<String, String> params = RequestParamBuilder.getInstance()
                .add("billNo", orderNum)
                .add("title", title)
                .add("totalFee", totalMoney + "")
                .add("returnUrl", returnUrl)
                .add("billTimeout", "")
                .add("channel", channel)
                .add("showUrl", "")
                .add("qrPayMode", qrPayMode)
                .add("useApp", useApp)
                .add("appId", payConfig.getPayAppid())
                .toMap();

        PrintWriter pw = null;
        String content = null;
        try {
            content = ApiCallUtil.call(payConfig.getPayUrl(), payConfig.getPayAppkey(), payConfig.getPaySecretkey(), params);
            logger.info(String.format("pay return message is %s", content));
            JSONObject jSONObject = JSON.parseObject(content);
            String status = jSONObject.getString("status");
            if ("OK".equals(status)) {
                //支付成功
                //微信扫码支付
                if ("WX_NATIVE".equals(channel)) {
                    //微信支付地址
                    String codeUrl = jSONObject.getJSONObject("result").getString("codeUrl");
                    model.addAttribute("wxUrl", codeUrl);
                    return "redirect:/order/wxPay.do";
                } else {
                    String html = jSONObject.getJSONObject("result").getString("html");
                    model.addAttribute("html", html);
                    return "redirect:/order/alipay.do";
                }
            } else {
                pw.write(jSONObject.getString("message"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            pw.write(e.getMessage()+"errorerrorerrorerrorerrorerrorerrorerrorerror");
        } finally {
            if (pw != null) {
                pw.flush();
                pw.close();
            }
        }
        return null;
    }

    @GetMapping("/alipay")
    public void alipay(@RequestParam String html, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter pw = response.getWriter();
        pw.write(html);
        pw.flush();
        pw.close();
    }

    @GetMapping("/wxPay")
    @ResponseBody
    public RtnData wxpay(@RequestParam String wxUrl) {
        RtnData result = RtnData.ok(wxUrl, null);
        return result;
    }

/*
    @RequestMapping(value = "/weixin")
    public String weixin(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        modelMap.put("codeUrl", request.getAttribute("codeUrl"));
        return "weixin";
    }*/


    /**
     * 请求银联回掉
     *
     * @return
     */
    @RequestMapping(value = "/payOrderBack", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public void payOrderBack(HttpServletRequest request, HttpServletResponse response
    ) {
        System.out.print("**************************************comming****************************************************");

        StringBuffer json = new StringBuffer();
        String line = null;
        try {
            request.setCharacterEncoding("utf-8");
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            JSONObject parse = JSON.parseObject(json.toString());
            OrderInfo orderInfo = null;
            orderInfo = iOrderService.getOrderInfoByOrderNum(parse.getString("transaction_id"));
            UserData userData = new UserData();
            userData.setName(orderInfo.getOrderUserName());
            userData.setUserId(orderInfo.getUserId());
            System.out.println("**************************************tradeSucess:"+parse.getBoolean("tradeSuccess")+"****************************************************");
            if (parse.getBoolean("tradeSuccess")) {
                if (orderInfo.getOrderStatus() < OrderStatusEnum.PAY_SUCCESS.getIndex()) {

                    orderInfo.setOrderStatus(OrderStatusEnum.PAY_SUCCESS.getIndex());
                    orderInfo.setHavePay(orderInfo.getOrderCashTotal());
                    iOrderService.updateOrderInfo(orderInfo);
                    List<RouterRule>routerRules= iRouterRuleSeivice.getRouterRuleByOrderNum(parse.getString("transaction_id"));
                    if(routerRules!=null && routerRules.size()>0){
                        for (RouterRule rr : routerRules){
                            iRouterRuleSeivice.updateStatus(rr.getId(),ChangeConstants.SOURCE_ROUTER_PAY_VERIFY);
                        }
                    }
                    // 创建支付流水
                    iPayLogService.addPayLog(orderInfo);
                    System.out.println("**************************************创建完支付流水****************************************************");
                     iPayService.doRouterRule(parse.getString("transaction_id"), userData);
                }

                System.out.println("**************************************orderNum:"+parse.getString("transaction_id")+"****************************************************");
                apiService.doPushStatic();
                response.getWriter().write("success");
            } else {
                orderInfo.setOrderStatus(OrderStatusEnum.PAR_FAIL.getIndex());
                iPayLogService.addPayLog(orderInfo);
                iOrderService.updateOrderInfo(orderInfo);
                response.getWriter().write("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
