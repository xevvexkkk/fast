package com.frame.fast.service.person;

import com.baomidou.mybatisplus.extension.service.IService;
import com.frame.fast.model.PersonAddressPurchaseInfo;
import com.frame.fast.model.ProductSort;

import java.util.List;

/**
 * <p>
 * 用户地址购买信息 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-08-20
 */
public interface IPersonAddressPurchaseInfoService extends IService<PersonAddressPurchaseInfo> {

    List<PersonAddressPurchaseInfo> getByUserIdOrAddress(Long userId, ProductSort productSort, Integer community, Integer area, String address);

    PersonAddressPurchaseInfo getByUserIdAndAddress(Long userId, ProductSort productSort, Integer community, Integer area, String address);
}
