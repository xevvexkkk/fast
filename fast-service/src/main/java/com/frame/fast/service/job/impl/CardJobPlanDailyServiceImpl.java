package com.frame.fast.service.job.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.fast.model.CardJobPlanDaily;
import com.frame.fast.model.CommunityEnum;
import com.frame.fast.repository.CardJobPlanDailyMapper;
import com.frame.fast.service.job.ICardJobPlanDailyService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 月卡作业（每日） 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-09-24
 */
@Service
public class CardJobPlanDailyServiceImpl extends ServiceImpl<CardJobPlanDailyMapper, CardJobPlanDaily> implements ICardJobPlanDailyService {
    @Resource
    private CardJobPlanDailyMapper cardJobPlanDailyMapper;

    @Override
    public List<CardJobPlanDaily> listByStaffId(@Nullable Integer staffId){
        QueryWrapper<CardJobPlanDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("staff_id",staffId);
        return cardJobPlanDailyMapper.selectList(wrapper);
    }

    @Override
    public List<CardJobPlanDaily> listByStaffIdAndComunity(@Nullable Integer staffId, @Nullable CommunityEnum community){
        QueryWrapper<CardJobPlanDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("staff_id",staffId);
        wrapper.eq("community",community);
        return cardJobPlanDailyMapper.selectList(wrapper);
    }

    @Override
    public List<Map<String, Object>> listCommunity(@Nullable Integer staffId){
        List<CommunityEnum> communities = cardJobPlanDailyMapper.getCommunity(staffId);
        List<Map<String,Object>> resultList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(communities)){
            communities.forEach(n->{
                Map<String,Object> one = new HashMap<>();
                one.put("value",n.getValue());
                one.put("name",n.getName());
                resultList.add(one);
            });
        }
        return resultList;
    }

}
