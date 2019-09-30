package com.frame.fast.cms.job;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.frame.fast.model.CardJobPlanDaily;
import com.frame.fast.model.CardJobPlanHistory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

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
public class CardJobPlanHistoryJobVo extends CardJobPlanHistory {

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

    public static List<CardJobPlanHistoryJobVo> tranFromOrigin(List<CardJobPlanHistory> cardJobPlanHistories){
        List<CardJobPlanHistoryJobVo> dailyJobVos = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(cardJobPlanHistories)){
            cardJobPlanHistories.forEach(n->{
                CardJobPlanHistoryJobVo vo = new CardJobPlanHistoryJobVo();
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
