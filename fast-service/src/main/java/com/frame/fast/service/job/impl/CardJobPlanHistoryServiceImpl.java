package com.frame.fast.service.job.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.fast.model.CardJobPlanHistory;
import com.frame.fast.repository.CardJobPlanHistoryMapper;
import com.frame.fast.service.job.ICardJobPlanHistoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 月卡作业（历史） 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-09-24
 */
@Service
public class CardJobPlanHistoryServiceImpl extends ServiceImpl<CardJobPlanHistoryMapper, CardJobPlanHistory> implements ICardJobPlanHistoryService {

}
