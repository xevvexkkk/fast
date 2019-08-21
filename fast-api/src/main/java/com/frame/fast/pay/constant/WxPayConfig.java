package com.frame.fast.pay.constant;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

/**
 * 小程序微信支付的配置文件
 * @author
 *
 */
public class WxPayConfig {

    @Autowired
    private Environment environment;

    @Value("${appId}")
    private String appId;

    //小程序appid
    public static final String appid = "wxbc1b80f39e9ef6a8";
    //微信支付的商户id
    public static final String mch_id = "1549465451";
    //微信支付的商户密钥
    public static final String key = "qpd0swdnqtzg0b8m8n71aaf5dxribma1";
    //支付成功后的服务器回调url
    public static final String notify_url = "https://??/??/weixin/api/wxNotify";
    //签名方式，固定值
    public static final String SIGNTYPE = "MD5";
    //交易类型，小程序支付的固定值为JSAPI
    public static final String TRADETYPE = "JSAPI";
    //微信统一下单接口地址
    public static final String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}
