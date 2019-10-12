package com.frame.fast.cms.job;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.frame.fast.model.CardJobPlanDaily;
import com.frame.fast.model.SingleJobPlan;
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
public class SingleJobPlanVo extends SingleJobPlan {

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

    public static List<SingleJobPlanVo> tranFromOrigin(List<SingleJobPlan> singleJobPlans){
        List<SingleJobPlanVo> singleJobPlanVos = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(singleJobPlans)){
            singleJobPlans.forEach(n->{
                SingleJobPlanVo vo = new SingleJobPlanVo();
                BeanUtils.copyProperties(n,vo);
                vo.setStatusDesc(n.getStatus().getName());
                vo.setCategoryDesc(n.getCategory().getName());
                vo.setCommunityDesc(n.getCommunity().getName());
                singleJobPlanVos.add(vo);
            });
        }
        return singleJobPlanVos;
    }

}
