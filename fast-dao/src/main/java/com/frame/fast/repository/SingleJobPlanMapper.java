package com.frame.fast.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.frame.fast.model.CommunityEnum;
import com.frame.fast.model.SingleJobPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 单次作业 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-10-08
 */
public interface SingleJobPlanMapper extends BaseMapper<SingleJobPlan> {

    List<CommunityEnum> getCommunity(@Param("staffId") Integer staffId);
}
