package com.frame.fast.service.custom;

import com.baomidou.mybatisplus.extension.service.IService;
import com.frame.fast.model.ProductSort;
import com.frame.fast.model.SingleProduct;

/**
 * <p>
 * 用户产品状态-单次 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-09-08
 */
public interface ISingleProductService extends IService<SingleProduct> {

    SingleProduct getSingle(Long id, ProductSort productSort);
}
