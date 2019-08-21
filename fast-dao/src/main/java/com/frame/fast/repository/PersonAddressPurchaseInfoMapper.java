package com.frame.fast.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.frame.fast.model.PersonAddressPurchaseInfo;
import com.frame.fast.model.ProductSort;

import java.util.List;

/**
 * <p>
 * 用户地址购买信息 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-08-20
 */
public interface PersonAddressPurchaseInfoMapper extends BaseMapper<PersonAddressPurchaseInfo> {

    List<PersonAddressPurchaseInfo> selectByUserInfo(Long userId, ProductSort productSort, Integer community, Integer area, String address);
}
