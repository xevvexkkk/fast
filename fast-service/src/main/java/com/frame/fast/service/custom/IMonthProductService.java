package com.frame.fast.service.custom;

import com.baomidou.mybatisplus.extension.service.IService;
import com.frame.fast.model.MonthProduct;
import com.frame.fast.model.ProductSort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户产品状态 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-08-12
 */
public interface IMonthProductService extends IService<MonthProduct> {

    List<MonthProduct> getByCustomId(Long customId);

    List<MonthProduct> list(Long customId);

    List<MonthProduct> getNotFinishedProducts(Long customId);

    List<MonthProduct> getCanNotRepayMonthProducts(Long customId);

    MonthProduct getByCustomIdAndCategory(Long customId, ProductSort productSort);

    void updateDailyRemainDays(List<Long> needMinusDayIds, List<Long> needInvalidIds, List<Long> needvaldIds);

    List<MonthProduct> getDailyDealProducts();
}
