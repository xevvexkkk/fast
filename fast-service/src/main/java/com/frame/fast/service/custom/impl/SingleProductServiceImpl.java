package com.frame.fast.service.custom.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.fast.model.ProductSort;
import com.frame.fast.model.SingleProduct;
import com.frame.fast.repository.SingleProductMapper;
import com.frame.fast.service.custom.ISingleProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户产品状态-单次 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-09-08
 */
@Service
public class SingleProductServiceImpl extends ServiceImpl<SingleProductMapper, SingleProduct> implements ISingleProductService {

    @Resource
    private SingleProductMapper singleProductMapper;

    @Override
    public SingleProduct getSingle(Long id, ProductSort productSort){
        QueryWrapper<SingleProduct> wrapper = new QueryWrapper<>();
        wrapper.eq("custom_id",id);
        wrapper.eq("product_sort",productSort);
        return singleProductMapper.selectOne(wrapper);
    }
}
