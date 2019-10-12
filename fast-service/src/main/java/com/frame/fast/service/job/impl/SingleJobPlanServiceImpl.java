package com.frame.fast.service.job.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.fast.model.CardJobPlanDaily;
import com.frame.fast.model.CommunityEnum;
import com.frame.fast.model.SingleJobPlan;
import com.frame.fast.repository.SingleJobPlanMapper;
import com.frame.fast.service.job.ISingleJobPlanService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 单次作业 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-10-08
 */
@Service
public class SingleJobPlanServiceImpl extends ServiceImpl<SingleJobPlanMapper, SingleJobPlan> implements ISingleJobPlanService {

    @Resource
    private SingleJobPlanMapper singleJobPlanMapper;

    @Override
    public List<SingleJobPlan> listByStaffId(@Nullable Integer staffId){
        QueryWrapper<SingleJobPlan> wrapper = new QueryWrapper<>();
        wrapper.eq("staff_id",staffId);
        return singleJobPlanMapper.selectList(wrapper);
    }

    @Override
    public List<SingleJobPlan> listByStaffIdAndComunity(@Nullable Integer staffId, @Nullable CommunityEnum community){
        QueryWrapper<SingleJobPlan> wrapper = new QueryWrapper<>();
        wrapper.eq("staff_id",staffId);
        wrapper.eq("community",community);
        return singleJobPlanMapper.selectList(wrapper);
    }

    @Override
    public List<Map<String, Object>> listCommunity(@Nullable Integer staffId){
        List<CommunityEnum> communities = singleJobPlanMapper.getCommunity(staffId);
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
