package com.frame.fast.acode;


import com.frame.fast.common.WxAccessTokenEntity;
import com.frame.fast.pay.constant.WxPayConfig;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class AcodeController {

    private Gson gson = new Gson();

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/QrCode")
    public void getQrCodeImage(HttpServletResponse response) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

// if you need to pass form parameters in request with headers.
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("grant_type", WxPayConfig.client_credential);
//        map.add("appid", WxPayConfig.appid);
//        map.add("secret", WxPayConfig.appSecret);
        Map<String,String> param = new HashMap<>();
        param.put("grant_type",WxPayConfig.client_credential);
        param.put("appid",WxPayConfig.appid);
        param.put("secret",WxPayConfig.appSecret);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//        String tarUrl = WxPayConfig.access_token_url + "?grant_type=" + WxPayConfig.client_credential + "&appid=" + WxPayConfig.appid + "&secret=" + WxPayConfig.appSecret;
        String tarUrl = WxPayConfig.access_token_url + "?grant_type=" + WxPayConfig.client_credential + "&appid={appid}&secret={secret}";
//        String httpResStr = PayUtil.httpRequest(tarUrl, "GET",null );
        String result = restTemplate.getForEntity(tarUrl, String.class,param).getBody();
        WxAccessTokenEntity tokenEntity = gson.fromJson(result,WxAccessTokenEntity.class);
        if(tokenEntity == null || StringUtils.isEmpty(tokenEntity.getAccess_token()) ){
            return;
        }
        map.add("scene", "10001");
//        map.add("access_token",tokenEntity.getAccess_token());
//        String result = PayUtil.httpRequest(WxPayConfig.pay_url, "POST", xml.toString());
        /*ResponseEntity<byte[]> buffer = restTemplate.postForEntity(WxPayConfig.generate_acode_url + tokenEntity.getAccess_token(),request, byte[].class);
        byte[] image = buffer.getBody();
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setHeader("Content-disposition","attachment; filename=_channel=10001.jpeg");
        int i=0;
        InputStream inputStream = new ByteArrayInputStream(image);
        OutputStream out = response.getOutputStream();

        try {
            byte[] buf = new byte[1024];
            while ((i = inputStream.read(buf, 0, 1024)) != -1) {
                out.write(buf, 0, i);
            }
            for(i=0 ; i<image.length ; i++){
                out.write(image[i]);
                out.flush();
            }

        }catch (IOException e){
            throw new RuntimeException(e);
        }finally {
            out.close();
        }*/
        RestTemplate rest = new RestTemplate();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            String url = "https://api.weixin.qq.com/wxaapp/createwxaqrcode?access_token="+tokenEntity.getAccess_token();
//            Map<String,Object> param = new HashMap<>();
            param.put("page", "pages/index/index");
            param.put("scene", "10001");
//            param.put("width", 430);
            log.info("调用生成微信URL接口传参:" + param);
            MultiValueMap<String, String> headerss = new LinkedMultiValueMap<>();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity requestEntity = new HttpEntity(param, headerss);
            ResponseEntity<byte[]> entity = rest.exchange(WxPayConfig.generate_acode_url + tokenEntity.getAccess_token(), HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
            log.info("调用小程序生成微信永久二维码URL接口返回结果:" + entity.getHeaders().toString() +  entity.getBody());
            byte[] results = entity.getBody();
            log.info(Base64.encodeBase64String(results));
            inputStream = new ByteArrayInputStream(results);

            File file = new File("C:/Users/xerre/Desktop/1.jpeg");
            if (!file.exists()){
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            log.error("调用小程序生成微信永久二维码URL接口异常",e);
        } finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*private Map getminiqrQr(String accessToken,HttpServletResponse response) {*/


}
