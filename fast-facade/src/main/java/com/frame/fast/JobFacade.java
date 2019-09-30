package com.frame.fast;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.frame.fast.model.*;
import com.frame.fast.service.custom.IMonthProductService;
import com.frame.fast.service.custom.ISingleProductService;
import com.frame.fast.service.job.ICardJobPlanDailyService;
import com.frame.fast.service.job.ICardJobPlanHistoryService;
import com.frame.fast.service.person.PersonInfoService;
import com.frame.fast.util.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JobFacade {

    @Resource
    private ICardJobPlanDailyService dailyService;

    @Resource
    private ICardJobPlanHistoryService historyService;

    @Resource
    private IMonthProductService monthProductService;

    @Resource
    private ISingleProductService singleProductService;

    @Resource
    private PersonInfoService personInfoService;
    @Resource
    private MailService mailService;
    /**
     * 同步日表数据到历史表
     * @param busDate
     */
    @Transactional
    public void synDaily2History(LocalDate busDate){
        if(busDate == null){
            log.info("budDate 为空");
            return;
        }
        log.info("begin synDaily2History {}",busDate);
        Map<String,Object> param = new HashMap<>();
        param.put("bus_date",busDate);
        List<CardJobPlanDaily> dailies = (List<CardJobPlanDaily>) dailyService.listByMap(param);
        if(CollectionUtils.isNotEmpty(dailies)){
            List<CardJobPlanHistory> histories = new ArrayList<>();
            dailies.forEach(n->{
                CardJobPlanHistory history = new CardJobPlanHistory();
                BeanUtils.copyProperties(n,history);
                histories.add(history);
            });
            if(CollectionUtils.isNotEmpty(histories)){
                param.clear();;
                param.put("bus_date",busDate);
                historyService.removeByMap(param);
                historyService.saveBatch(histories);
            }
        }
    }

    /**
     * 生成每日分类/代扔作业计划
     */
    @Transactional
    public void generateDailyCardJobPlan(LocalDate busDate){
        if(busDate == null){
            log.warn("busDate 为空");
            return;
        }
        List<MonthProduct> dailyDealProducts = monthProductService.getDailyDealProducts();
        if(CollectionUtils.isEmpty(dailyDealProducts)){
            log.warn("今日无分类/代扔作业计划需要生成");
            return;
        }
        List<Long> notAccocatedIds = new ArrayList<>();
        dailyDealProducts.forEach(n->{
            if(n.getStaffId() == null){
                log.warn("用户Id {} 未分配代扔员，请及时前往管理系统进行维护",n.getCustomId());
                notAccocatedIds.add(n.getCustomId());
            }
        });
        if(CollectionUtils.isNotEmpty(notAccocatedIds)){
            mailService.sendTextMail(FastConstant.ORDER_NOTIFY_MAIN_LIST[0],FastConstant.JOB_NOTIFY_TITLE,FastConstant.getJobNotifyContent(notAccocatedIds));
        }
        List<Long> customIds = dailyDealProducts.stream().map(MonthProduct::getCustomId).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(customIds)){
            log.warn("无需要生成作业的用户，需要确认！");
            return;
        }
        Collection<PersonInfo> personInfos = personInfoService.listByIds(customIds);
        Map<Long,PersonInfo> personInfoMap= personInfos.stream().collect(Collectors.toMap(n->n.getId(), Function.identity()));
        List<CardJobPlanDaily> dailies = new ArrayList<>();
        dailyDealProducts.forEach(n->{
            CardJobPlanDaily daily = new CardJobPlanDaily();
            daily.setStaffId(n.getStaffId());
            daily.setAlterable(true);
            //如果未查到分配的代扔员，需要发送告警邮件
            PersonInfo personInfo = personInfoMap.get(n.getCustomId());
            if(personInfo == null){
                log.warn("用户Id {} 信息异常",n.getCustomId());
            }else {
                daily.setAddress(personInfo.getAddress());
                daily.setCommunity(personInfo.getCommunity());
                daily.setOpenId(personInfo.getOpenId());
            }
            daily.setRemainNum(n.getRemainNum());
            daily.setCustomId(n.getCustomId());
            daily.setStatus(JobStatus.INIT);
            daily.setBusDate(busDate);
            daily.setCategory(n.getCategory());
            dailies.add(daily);
        });
        Map<String,Object> param = new HashMap<>();
        param.put("bus_date",busDate);
        dailyService.removeByMap(param);
        dailyService.saveBatch(dailies);
    }
}
