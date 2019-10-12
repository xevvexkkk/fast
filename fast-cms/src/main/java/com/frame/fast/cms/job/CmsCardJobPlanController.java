package com.frame.fast.cms.job;


import com.frame.fast.model.*;
import com.frame.fast.service.job.ICardJobPlanDailyService;
import com.frame.fast.service.job.ICardJobPlanHistoryService;
import com.frame.fast.service.job.ISingleJobPlanService;
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

/**
 * <p>
 * 月卡作业（每日） 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-09-24
 */
@Controller
@RequestMapping("/job")
public class CmsCardJobPlanController {

    private Gson gson = new Gson();
    @Resource
    private ICardJobPlanDailyService cardJobPlanDailyService;
    @Resource
    private ICardJobPlanHistoryService historyService;
    @Resource
    private ISingleJobPlanService singleJobPlanService;
    @Resource
    private IStaffService staffService;
    @RequestMapping("/daily/toList")
    public String toList(Model model){
        model.addAttribute("jobStatuses",JobStatus.values());
        List<Staff> staff = staffService.list();
        model.addAttribute("staffs",staff);
        return "card_job_daily";
    }

    @RequestMapping("/history/toList")
    public String toHistoryList(Model model){
        model.addAttribute("jobStatuses",JobStatus.values());
        return "card_job_history";
    }

    @GetMapping("/daily/list")
    @ResponseBody
    public String list(Model model){
        List<CardJobPlanDaily> list = cardJobPlanDailyService.list();
        model.addAttribute("jobStatuses",JobStatus.values());
        return JsonUtils.toJson(CardJobPlanDailyVo.tranFromOrigin(list));
    }

    @PostMapping("/daily/doEdit")
    @ResponseBody
    public ResponseVo doEdit(CardJobPlanDaily cardJobPlanDaily){
        cardJobPlanDailyService.updateById(cardJobPlanDaily);
        return ResponseVo.successVo();
    }

    @GetMapping("/history/list")
    @ResponseBody
    public String historyList(Model model){
        List<CardJobPlanHistory> list = historyService.list();
        model.addAttribute("jobStatuses",JobStatus.values());
        return JsonUtils.toJson(CardJobPlanHistoryVo.tranFromOrigin(list));
    }

    @RequestMapping("/single/toList")
    public String toSingleList(Model model){
        model.addAttribute("jobStatuses",JobStatus.values());
        List<Staff> staff = staffService.list();
        model.addAttribute("staffs",staff);
        return "single_job_list";
    }

    @GetMapping("/single/list")
    @ResponseBody
    public String listSingles(Model model){
        List<SingleJobPlan> list = singleJobPlanService.list();
        model.addAttribute("jobStatuses",JobStatus.values());
        return JsonUtils.toJson(SingleJobPlanVo.tranFromOrigin(list));
    }

    @PostMapping("/single/doEdit")
    @ResponseBody
    public ResponseVo doEdit(SingleJobPlan singleJobPlan){
        singleJobPlanService.updateById(singleJobPlan);
        return ResponseVo.successVo();
    }
}
