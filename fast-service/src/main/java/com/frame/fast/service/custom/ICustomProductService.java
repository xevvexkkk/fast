package com.frame.fast.service.custom;

import com.baomidou.mybatisplus.extension.service.IService;
import com.frame.fast.model.CustomProduct;
import com.frame.fast.model.ProductSort;

import java.util.List;

/**
 * <p>
 * 用户产品状态 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-08-12
 */
public interface ICustomProductService extends IService<CustomProduct> {

    List<CustomProduct> getByCustomId(Long customId);

    CustomProduct getByCustomIdAndProductSort(Long customId, ProductSort productSort);

    List<CustomProduct> getValidMonthProduct(Long customId, ProductSort productSort);
}
