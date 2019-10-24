package com.frame.fast.pay;

import com.frame.fast.PayFacade;
import com.frame.fast.model.ResponseVo;
import com.frame.fast.model.*;
import com.frame.fast.pay.constant.WxPayConfig;
import com.frame.fast.service.order.IOrderService;
import com.frame.fast.service.person.PersonInfoService;
import com.frame.fast.util.IpUtil;
import com.frame.fast.util.PayUtil;
import com.frame.fast.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class PayController {

    @Resource
    private IOrderService orderService;
    @Resource
    private PayFacade payFacade;
    @Resource
    private PersonInfoService personInfoService;

    /**
     * @Description: 发起微信支付
     * @param request
     */
    @PostMapping("/pay")
    public ResponseVo wxPay(@RequestParam String openId, HttpServletRequest request,@RequestParam ProductSort productCode,@RequestParam Integer fee){
        try{
            if(StringUtils.isEmpty(openId)){
                return ResponseVo.failVo("openid cann`t by empty");
            }
            if(productCode == null){
                return ResponseVo.failVo("productCode cann`t by null");
            }
            String msg = "";
//            ProductSort productSort = ProductSort.forCode(productCode);
            if(productCode == null){
                return ResponseVo.failVo("未查询到对应的商品信息");
            }
            CheckResult checkResult = payFacade.checkBeforeOrder(openId, msg, productCode);
            if(!checkResult.isResult()){
                return ResponseVo.failVo(checkResult.getMsg());
            }

            PersonInfo user = personInfoService.getByOpenId(openId);

            //生成的随机字符串
            String nonce_str = StringUtil.getRandomStringByLength(32);
            //商品名称
            String body = productCode.getName();
            //获取客户端的ip地址
            String spbill_create_ip = IpUtil.getIpAddr(request);

            Long orderId = payFacade.generateOrderId(user.getId());
            //组装参数，用户生成统一下单接口的签名
            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appid", WxPayConfig.appid);
            packageParams.put("mch_id", WxPayConfig.mch_id);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", String.valueOf(orderId));//商户订单号
            packageParams.put("total_fee", String.valueOf(fee));//支付金额，这边需要转成字符串类型，否则后面的签名会失败
            packageParams.put("spbill_create_ip", spbill_create_ip);
            packageParams.put("notify_url", WxPayConfig.notify_url);//支付成功后的回调地址
            packageParams.put("trade_type", WxPayConfig.TRADETYPE);//支付方式
            packageParams.put("openid", String.valueOf(openId));

            String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

            //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mysign = PayUtil.sign(prestr, WxPayConfig.key, "utf-8").toUpperCase();

            //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            StringBuffer xml = new StringBuffer();
             xml.append("<xml>")
                     .append("<appid>").append(WxPayConfig.appid).append("</appid>")
                     .append("<body><![CDATA[").append(body).append("]]></body>")
                     .append("<mch_id>").append(WxPayConfig.mch_id).append("</mch_id>")
                     .append("<nonce_str>").append(nonce_str).append("</nonce_str>")
                     .append("<notify_url>").append(WxPayConfig.notify_url).append("</notify_url>")
                     .append("<openid>").append(openId).append("</openid>")
                     .append("<out_trade_no>").append(orderId).append("</out_trade_no>")
                     .append("<spbill_create_ip>").append(spbill_create_ip).append("</spbill_create_ip>")
                     .append("<total_fee>").append(fee).append("</total_fee>")
                     .append("<trade_type>").append(WxPayConfig.TRADETYPE).append("</trade_type>")
                     .append("<sign>").append(mysign).append("</sign>")
                     .append("</xml>");

            System.out.println("调试模式_统一下单接口 请求XML数据：" + xml);

            //调用统一下单接口，并接受返回的结果
            String result = PayUtil.httpRequest(WxPayConfig.pay_url, "POST", xml.toString());

            System.out.println("调试模式_统一下单接口 返回XML数据：" + result);

            // 将解析结果存储在HashMap中
            Map map = PayUtil.doXMLParse(result);

            String return_code = (String) map.get("return_code");//返回状态码
            String return_msg = String.valueOf(map.get("return_msg"));
            Map<String, Object> response = new HashMap<String, Object>();//返回给小程序端需要的参数
            if(return_code.equals("SUCCESS")){
                String prepay_id = (String) map.get("prepay_id");//返回的预付单信息
                response.put("nonceStr", nonce_str);
                response.put("package", "prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                response.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                //拼接签名需要的参数
                String stringSignTemp = "appId=" + WxPayConfig.appid + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id+ "&signType=MD5&timeStamp=" + timeStamp;
                //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = PayUtil.sign(stringSignTemp, WxPayConfig.key, "utf-8").toUpperCase();
                response.put("order_id",orderId);
                response.put("paySign", paySign);
                response.put("address",user.getAddress());
                Order order = new Order();
                order.setOrderStatus(OrderStatus.SUCCESS);
                order.setCustomId(user.getId());
                order.setPrepayId(prepay_id);
                order.setSpbillCreateIp(spbill_create_ip);
                order.setOpenId(openId);
                order.setProductSort(productCode);
                order.setTotalFee(productCode.getCode());
                order.setRealFee(fee);
                order.setOrderId(orderId);
                orderService.save(order);
            }else {
                return ResponseVo.failVo(return_msg);
            }

            response.put("appid", WxPayConfig.appid);

            return ResponseVo.successVo("",response);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @Description:微信支付通知
     * @return
     * @throws Exception
     */
    @PostMapping(value="/wxNotify")
    public void wxNotify(HttpServletRequest request,HttpServletResponse response) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine()) != null){
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";
        System.out.println("接收到的报文：" + notityXml);

        Map map = PayUtil.doXMLParse(notityXml);

        String returnCode = (String) map.get("return_code");
        if("SUCCESS".equals(returnCode)){
            //验证签名是否正确
            Map<String, String> validParams = PayUtil.paraFilter(map);  //回调验签时需要去除sign和空值参数
            String validStr = PayUtil.createLinkString(validParams);//把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            String sign = PayUtil.sign(validStr, WxPayConfig.key, "utf-8").toUpperCase();//拼装生成服务器端验证的签名
            //根据微信官网的介绍，此处不仅对回调的参数进行验签，还需要对返回的金额与系统订单的金额进行比对等
            if(sign.equals(map.get("sign"))){
                /**此处添加自己的业务逻辑代码start**/


                /**此处添加自己的业务逻辑代码end**/
                //通知微信服务器已经支付成功
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            }
        }else{
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        System.out.println(resXml);
        System.out.println("微信支付回调数据结束");


        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

    @PostMapping("/payNotify")
    public ResponseVo payResultCallBack(String errMsg,Long orderId){
        Order order = orderService.getOrderByOrderId(orderId);
        if(order == null){
            return ResponseVo.failVo("订单号异常");
        }
        String result = org.apache.commons.lang3.StringUtils.substringAfterLast(errMsg,":");
        OrderStatus orderStatus= OrderStatus.forName(result);
        if(Arrays.asList(FastConstant.WHITE_LIST).contains(order.getCustomId()) ){
            orderStatus = OrderStatus.SUCCESS;
        }

        if(orderStatus == null){
            orderStatus = OrderStatus.ABNORMAL;
            log.warn("订单号{}，状态异常",order);
        }
        order.setOrderStatus(orderStatus);
        if(orderStatus == OrderStatus.SUCCESS){
            payFacade.syncOrderInfoAfterPayComplete(order);
        }
        return ResponseVo.successVo("",null);
    }
}
