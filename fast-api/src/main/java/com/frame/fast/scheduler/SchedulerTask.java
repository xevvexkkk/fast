package com.frame.fast.scheduler;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.frame.fast.model.FastConstant;
import com.frame.fast.model.MonthCardStatus;
import com.frame.fast.model.MonthProduct;
import com.frame.fast.model.PersonInfo;
import com.frame.fast.service.custom.IMonthProductService;
import com.frame.fast.service.person.PersonInfoService;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RestController
public class SchedulerTask {

    @Resource
    private IMonthProductService monthProductService;

    @Resource
    private PersonInfoService personInfoService;
    @Resource
    private ValueOperations<String, String> valueOperations;

    @Scheduled(cron = "0 0 0 * * ? ")
    @GetMapping("/refreshMonthCardInfo")
    public void refreshMonthCardInfo(){
        List<MonthProduct> dailyDealProducts = monthProductService.getDailyDealProducts();
        List<Long> needMinusDayIds = new ArrayList<>();
        List<Long> needInvalidIds = new ArrayList<>();
        List<Long> needInvaldCustIds = new ArrayList<>();
        List<Long> needvaldIds = new ArrayList<>();
        for(MonthProduct monthProduct : dailyDealProducts){
            if(monthProduct.getStatus().equals(MonthCardStatus.VALID)){
                needMinusDayIds.add(monthProduct.getId());
                if(monthProduct.getRemainNum() == 1){
                    needInvalidIds.add(monthProduct.getId());
                    needInvaldCustIds.add(monthProduct.getCustomId());
                }
            }
        }
        List<MonthProduct> wait2ValidProducts = dailyDealProducts.stream().filter(n->n.getStatus().equals(MonthCardStatus.INVALID)).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(wait2ValidProducts)){
            for(MonthProduct monthProduct : wait2ValidProducts){
                //激活待生效的月卡
                if(needInvaldCustIds.contains(monthProduct.getCustomId())){
                    needvaldIds.add(monthProduct.getId());
                }
            }
        }
        monthProductService.updateDailyRemainDays(needMinusDayIds,needInvalidIds,needvaldIds);
        dailyDealProducts.forEach(n->{
            valueOperations.set(FastConstant.MONTH_CARD + n.getCustomId(),null);
        });
    }

}
