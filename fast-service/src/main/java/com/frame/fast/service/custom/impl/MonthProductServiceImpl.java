package com.frame.fast.service.custom.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.fast.model.CardCategory;
import com.frame.fast.model.MonthCardStatus;
import com.frame.fast.model.MonthProduct;
import com.frame.fast.model.ProductSort;
import com.frame.fast.repository.MonthProductMapper;
import com.frame.fast.service.custom.IMonthProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
public class MonthProductServiceImpl extends ServiceImpl<MonthProductMapper, MonthProduct> implements IMonthProductService {

    @Resource
    private MonthProductMapper monthProductMapper;

    @Override
    public List<MonthProduct> getByCustomId(Long customId){
        QueryWrapper<MonthProduct> wrapper = new QueryWrapper<>();
        wrapper.eq("custom_id",customId);
        return monthProductMapper.selectList(wrapper);
    }

    @Override
    public List<MonthProduct> getNotFinishedProducts(Long customId){
        QueryWrapper<MonthProduct> wrapper = new QueryWrapper<>();
        wrapper.eq("custom_id",customId);
        wrapper.gt("remain_num",0);
        return monthProductMapper.selectList(wrapper);
    }

    @Override
    public List<MonthProduct> getCanNotRepayMonthProducts(Long customId){
        QueryWrapper<MonthProduct> wrapper = new QueryWrapper<>();
//        List<ProductSort> productSorts = ProductSort.getAllMonthProducts();
        wrapper.eq("custom_id",customId);
//        wrapper.in("product_sort",productSorts);
        wrapper.gt("remain_num", 15);
        return monthProductMapper.selectList(wrapper);
    }

    @Override
    public MonthProduct getByCustomIdAndCategory(Long customId, ProductSort productSort){
        QueryWrapper<MonthProduct> wrapper = new QueryWrapper<>();
        CardCategory cardCategory = CardCategory.getCategory(productSort);
        if(cardCategory ==null){
            throw new RuntimeException("产品分类异常");
        }
        wrapper.eq("custom_id",customId);
        wrapper.eq("category",cardCategory);
        return monthProductMapper.selectOne(wrapper);
    }

    @Override
    @Transactional
    public void updateDailyRemainDays(List<Long> needMinusDayIds, List<Long> needInvalidIds, List<Long> needvaldIds){
        UpdateWrapper<MonthProduct> wrapper = new UpdateWrapper();
        if(CollectionUtils.isNotEmpty(needMinusDayIds)){
            wrapper.setSql("remain_num=remain_num-1");
            wrapper.in("id",needMinusDayIds);
            monthProductMapper.update(new MonthProduct(),wrapper);
        }
        if(CollectionUtils.isNotEmpty(needInvalidIds)){
            wrapper = new UpdateWrapper<>();
            wrapper.set("status", MonthCardStatus.INVALID);
            wrapper.in("id",needInvalidIds);
            monthProductMapper.update(new MonthProduct(),wrapper);
        }
        if(CollectionUtils.isNotEmpty(needvaldIds)){
            wrapper = new UpdateWrapper<>();
            wrapper.set("status", MonthCardStatus.VALID);
            wrapper.in("id",needvaldIds);
            monthProductMapper.update(new MonthProduct(),wrapper);

        }
    }

    @Override
    public List<MonthProduct> getDailyDealProducts(){
        QueryWrapper<MonthProduct> wrapper = new QueryWrapper<>();
        wrapper.gt("end_effect_date", LocalDateTime.now());
        wrapper.gt("remain_num",0);
        return monthProductMapper.selectList(wrapper);
    }
}
