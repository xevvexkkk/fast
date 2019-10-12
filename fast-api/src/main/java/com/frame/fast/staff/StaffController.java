package com.frame.fast.staff;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.frame.fast.model.*;
import com.frame.fast.service.job.ICardJobPlanDailyService;
import com.frame.fast.service.job.ISingleJobPlanService;
import com.frame.fast.service.staff.IStaffService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Resource
    private IStaffService staffService;
    @Resource
    private ICardJobPlanDailyService dailyService;
    @Resource
    private ISingleJobPlanService singleJobPlanService;
    @GetMapping("/login")
    public ResponseVo login(@RequestParam String username,@RequestParam String password){
        Staff staff = staffService.getByUserNameAndPassword(username, password);
        if(staff == null){
            return ResponseVo.failVo("用户不存在");
        }
        if(!staff.getValid()){
            return ResponseVo.failVo("该用户已被禁用");
        }
        return ResponseVo.successVo(StaffVo.buildVo(staff));
    }

    @GetMapping("/job/card")
    public ResponseVo getCardJob(@RequestParam Integer staffId){
        List<CardJobPlanDaily> dailies = dailyService.listByStaffId(staffId);
        if(CollectionUtils.isEmpty(dailies)){
            return ResponseVo.failVo("暂无需要处理的日常订单");
        }
        return ResponseVo.successVo(dailies);
    }

    @GetMapping("/job/card/community")
    public ResponseVo getCardJobCommunity(@RequestParam Integer staffId){
        List<Map<String, Object>> list = dailyService.listCommunity(staffId);
        if(CollectionUtils.isEmpty(list)){
            return ResponseVo.failVo("暂无需要处理的小区");
        }
        return ResponseVo.successVo(list);
    }

    @GetMapping("/job/single")
    public ResponseVo getSinleJob(@RequestParam Integer staffId){
        List<SingleJobPlan> dailies = singleJobPlanService.listByStaffId(staffId);
        if(CollectionUtils.isEmpty(dailies)){
            return ResponseVo.failVo("暂无需要处理的订单");
        }
        return ResponseVo.successVo(dailies);
    }

    @GetMapping("/job/single/community")
    public ResponseVo getSingleJobCommunity(@RequestParam Integer staffId){
        List<Map<String, Object>> list = singleJobPlanService.listCommunity(staffId);
        if(CollectionUtils.isEmpty(list)){
            return ResponseVo.failVo("暂无需要处理的小区");
        }
        return ResponseVo.successVo(list);
    }
}
