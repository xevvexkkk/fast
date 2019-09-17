package com.frame.fast.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户产品状态
 * </p>
 *
 * @author jobob
 * @since 2019-08-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class MonthProduct extends BaseEntity {

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
     * 剩余次/天数
     */
    private Integer remainNum;
    /**
     * 失效日期
     */
    private LocalDateTime endEffectDate;

    /**
     * 月卡状态
     */
    private MonthCardStatus status;

    /**
     * 产品类型
     */
    private CardCategory category;

    @TableField(exist=false)
    @JsonFormat( pattern = "yyyy-MM-dd")
    private LocalDateTime endDate;

}
