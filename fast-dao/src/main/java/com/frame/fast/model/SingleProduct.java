package com.frame.fast.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户产品状态-单次
 * </p>
 *
 * @author jobob
 * @since 2019-09-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SingleProduct extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户Id
     */
    private Long customId;

    /**
     * openid
     */
    private String openId;

    /**
     * 月卡分类
     */
    private ProductSort productSort;

    /**
     * 剩余次/天数
     */
    private Integer remainNum;


}
