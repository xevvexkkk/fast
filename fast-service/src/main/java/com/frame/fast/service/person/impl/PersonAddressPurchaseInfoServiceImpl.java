package com.frame.fast.service.person.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.fast.model.PersonAddressPurchaseInfo;
import com.frame.fast.model.ProductSort;
import com.frame.fast.repository.PersonAddressPurchaseInfoMapper;
import com.frame.fast.service.person.IPersonAddressPurchaseInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户地址购买信息 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-08-20
 */
@Service
public class PersonAddressPurchaseInfoServiceImpl extends ServiceImpl<PersonAddressPurchaseInfoMapper, PersonAddressPurchaseInfo> implements IPersonAddressPurchaseInfoService {

    @Resource
    private PersonAddressPurchaseInfoMapper mapper;

    /**
     * 某个用户或者某个地址下面的购买记录
     * @param userId
     * @param productSort
     * @param address
     * @return
     */
    @Override
    public List<PersonAddressPurchaseInfo> getByUserIdOrAddress(Long userId, ProductSort productSort, Integer community, Integer area, String address){
        return mapper.selectByUserInfo(userId,productSort,community,area,address);
    }

    @Override
    public PersonAddressPurchaseInfo getByUserIdAndAddress(Long userId, ProductSort productSort, Integer community, Integer area, String address){
        QueryWrapper<PersonAddressPurchaseInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("product_sort",productSort);
        wrapper.eq("community",community);
        wrapper.eq("area",area);
        wrapper.eq("address",address);
        return mapper.selectOne(wrapper);
    }
}
