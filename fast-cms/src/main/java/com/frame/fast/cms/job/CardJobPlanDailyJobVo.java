package com.frame.fast.cms.job;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.frame.fast.cms.user.UserInfoVo;
import com.frame.fast.model.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 月卡作业（每日）
 * </p>
 *
 * @author jobob
 * @since 2019-09-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CardJobPlanDailyJobVo extends CardJobPlanDaily {

    /**
     * 处理状态
     */
    private String statusDesc;

    /**
     * 产品类型
     */
    private String categoryDesc;

    /**
     * 社区
     */
    private String communityDesc;

    public static List<CardJobPlanDailyJobVo> tranFromOrigin(List<CardJobPlanDaily> cardJobPlanDailies){
        List<CardJobPlanDailyJobVo> dailyJobVos = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(cardJobPlanDailies)){
            cardJobPlanDailies.forEach(n->{
                CardJobPlanDailyJobVo vo = new CardJobPlanDailyJobVo();
                BeanUtils.copyProperties(n,vo);
                vo.setStatusDesc(n.getStatus().getName());
                vo.setCategoryDesc(n.getCategory().getName());
                vo.setCommunityDesc(n.getCommunity().getName());
                dailyJobVos.add(vo);
            });
        }
        return dailyJobVos;
    }

}
