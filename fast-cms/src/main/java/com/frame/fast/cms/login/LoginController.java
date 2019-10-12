package com.frame.fast.cms.login;

import com.frame.fast.model.ResponseVo;
import com.frame.fast.model.Staff;
import com.frame.fast.service.staff.IStaffService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class LoginController {

    @Resource
    private IStaffService staffService;

    @GetMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @PostMapping("/doLogin")
    @ResponseBody
    public ResponseVo login(@RequestParam String username , @RequestParam String password){
        Staff staff = staffService.getByUserNameAndPassword(username, password);
        if(staff == null){
            return ResponseVo.failVo("用户不存在，或用户名密码不匹配");
        }
        return ResponseVo.successVo();
    }
}
