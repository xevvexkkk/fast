package com.frame.fast.cms.staff;

import com.frame.fast.model.ResponseVo;
import com.frame.fast.model.Staff;
import com.frame.fast.model.StaffPost;
import com.frame.fast.service.staff.IStaffService;
import com.frame.fast.util.JsonUtils;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class CmsStaffController {

    private Gson gson = new Gson();

    @Resource
    private IStaffService staffService;
    @GetMapping("/toList")
    public String toList(Model model){
        model.addAttribute("posts", StaffPost.values());
        return "staff_list";
    }

    @GetMapping("/list")
    @ResponseBody
    public String list(){
        List<Staff> list = staffService.list();
        return JsonUtils.toJson(StaffVo.tranFromOrigin(list));
    }

    @GetMapping("/toEdit")
    public String toEdit(){
        return "";
    }

    @PostMapping("/doEdit")
    @ResponseBody
    public ResponseVo doEdit(Staff staff){
        staffService.saveOrUpdate(staff);
        return ResponseVo.successVo();
    }

    @PostMapping("/resetPassword")
    @ResponseBody
    public ResponseVo resetPassword(Staff staff){
        staff.setPassword("123456");
        staffService.saveOrUpdate(staff);
        return ResponseVo.successVo();
    }
}
