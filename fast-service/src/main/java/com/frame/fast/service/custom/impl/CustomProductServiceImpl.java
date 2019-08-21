package com.frame.fast.service.custom.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.fast.model.CustomProduct;
import com.frame.fast.model.ProductSort;
import com.frame.fast.repository.CustomProductMapper;
import com.frame.fast.service.custom.ICustomProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户产品状态 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-08-12
 */
@Service
public class CustomProductServiceImpl extends ServiceImpl<CustomProductMapper, CustomProduct> implements ICustomProductService {

    @Resource
    private CustomProductMapper customProductMapper;

    @Override
    public List<CustomProduct> getByCustomId(Long customId){
        QueryWrapper<CustomProduct> wrapper = new QueryWrapper<>();
        wrapper.eq("custom_id",customId);
        return customProductMapper.selectList(wrapper);
    }
    @Override
    public CustomProduct getByCustomIdAndProductSort(Long customId, ProductSort productSort){
        QueryWrapper<CustomProduct> wrapper = new QueryWrapper<>();
        wrapper.eq("custom_id",customId);
        wrapper.eq("product_sort",productSort);
        return customProductMapper.selectOne(wrapper);
    }

    @Override
    public List<CustomProduct> getValidMonthProduct(Long customId,ProductSort productSort){
        QueryWrapper<CustomProduct> wrapper = new QueryWrapper<>();
        wrapper.eq("custom_id",customId);
        wrapper.eq("product_sort", productSort);
        return customProductMapper.selectList(wrapper);
    }
}
