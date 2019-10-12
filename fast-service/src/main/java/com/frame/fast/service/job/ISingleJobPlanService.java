package com.frame.fast.service.job;

import com.baomidou.mybatisplus.extension.service.IService;
import com.frame.fast.model.CommunityEnum;
import com.frame.fast.model.SingleJobPlan;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 单次作业 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-10-08
 */
public interface ISingleJobPlanService extends IService<SingleJobPlan> {

    List<SingleJobPlan> listByStaffId(@Nullable Integer staffId);

    List<SingleJobPlan> listByStaffIdAndComunity(@Nullable Integer staffId, @Nullable CommunityEnum community);

    List<Map<String, Object>> listCommunity(@Nullable Integer staffId);
}
