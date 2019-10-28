package com.frame.fast.handler;


import com.frame.fast.model.FastConstant;
import com.frame.fast.util.MailService;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.Resource;

@ControllerAdvice
public class GlobalExceptionHandler {
    @Resource
    private MailService mailService;

    //声明要捕获的异常
    @ExceptionHandler(Exception.class)
    public void defultExcepitonHandler(Exception e) {
        mailService.sendTextMail(FastConstant.EXCEPTION_WARN_LIST[0],"系统异常告警",e.getMessage());
    }

}
