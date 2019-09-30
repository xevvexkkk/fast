package com.frame.fast.service.job;

import com.baomidou.mybatisplus.extension.service.IService;
import com.frame.fast.model.CardJobPlanDaily;
import com.frame.fast.model.CommunityEnum;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 月卡作业（每日） 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-09-24
 */
public interface ICardJobPlanDailyService extends IService<CardJobPlanDaily> {

    List<CardJobPlanDaily> listByStaffId(@Nullable Integer staffId);

    List<CardJobPlanDaily> listByStaffIdAndComunity(@Nullable Integer staffId, @Nullable CommunityEnum community);

    List<Map<String, Object>> listCommunity(@Nullable Integer staffId);
}
