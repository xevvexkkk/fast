package com.frame.fast.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.frame.fast.model.CardJobPlanDaily;
import com.frame.fast.model.CommunityEnum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 月卡作业（每日） Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-09-24
 */
public interface CardJobPlanDailyMapper extends BaseMapper<CardJobPlanDaily> {

    List<CommunityEnum> getCommunity(@Param("staffId") Integer staffId);

}
