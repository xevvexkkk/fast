package com.frame.fast.job;

import com.frame.fast.model.CardJobPlanDaily;
import com.frame.fast.model.CommunityEnum;
import com.frame.fast.model.JobStatus;
import com.frame.fast.model.ResponseVo;
import com.frame.fast.service.job.ICardJobPlanDailyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/job")
public class CardJobPlanController {

    @Resource
    private ICardJobPlanDailyService cardJobPlanDailyService;

    @GetMapping("/list")
    public ResponseVo listByStaffId(@RequestParam Integer staffId,@RequestParam CommunityEnum community){
        List<CardJobPlanDaily> cardJobPlanDailies = cardJobPlanDailyService.listByStaffIdAndComunity(staffId,community);
        return ResponseVo.successVo(cardJobPlanDailies);
    }

    @GetMapping("/community")
    public ResponseVo listCommunity(@RequestParam Integer staffId){
        List<Map<String, Object>> maps = cardJobPlanDailyService.listCommunity(staffId);
        return ResponseVo.successVo(maps);
    }

    @GetMapping("/updateStatus")
    public ResponseVo updateStatus(@RequestParam Long id, @RequestParam JobStatus status){
        if(status == JobStatus.INIT){
            return ResponseVo.failVo("如需重置该笔订单状态，请点击重置按钮");
        }
        CardJobPlanDaily daily = new CardJobPlanDaily();
        daily.setStatus(status);
        daily.setId(id);
        cardJobPlanDailyService.updateById(daily);
        return ResponseVo.successVo();
    }

    @GetMapping("/resetStatus")
    public ResponseVo resetStatus(@RequestParam Long id){
        CardJobPlanDaily origin = cardJobPlanDailyService.getById(id);
        if(origin == null){
            return ResponseVo.failVo("未查询到该订单");
        }
        if(!origin.getAlterable()){
            return ResponseVo.failVo("您已重置过该订单，不可再次重置");
        }
        CardJobPlanDaily daily = new CardJobPlanDaily();
        daily.setStatus(JobStatus.INIT);
        daily.setId(id);
        daily.setAlterable(false);
        cardJobPlanDailyService.updateById(daily);
        return ResponseVo.successVo();
    }


}
