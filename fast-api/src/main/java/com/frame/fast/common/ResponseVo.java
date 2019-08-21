package com.frame.fast.common;

import lombok.Data;

@Data
public class ResponseVo {

    private String message;

    private boolean success;

    private Object data;

    public static ResponseVo successVo(String message,Object data){
        ResponseVo responseVo = new ResponseVo();
        responseVo.message = message;
        responseVo.success = true;
        responseVo.data = data;
        return responseVo;
    }

    public static ResponseVo successVo(Object data){
        ResponseVo responseVo = new ResponseVo();
        responseVo.message = "请求成功";
        responseVo.success = true;
        responseVo.data = data;
        return responseVo;
    }

    public static ResponseVo failVo(String message){
        ResponseVo responseVo = new ResponseVo();
        responseVo.message = message;
        responseVo.success = false;
        return responseVo;
    }
}
