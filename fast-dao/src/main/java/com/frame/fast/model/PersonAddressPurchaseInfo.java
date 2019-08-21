package com.frame.fast.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户地址购买信息
 * </p>
 *
 * @author jobob
 * @since 2019-08-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PersonAddressPurchaseInfo extends BaseEntity {


    /**
     * 小区
     */
    private Integer community;

    /**
     * 区域
     */
    private Integer area;

    /**
     * 地址
     */
    private String address;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * openid
     */
    private String openId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 产品分类
     */
    private ProductSort productSort;

    /**
     * 购买次数
     */
    private Integer purchaseNum;


}
