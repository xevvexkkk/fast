package com.frame.fast.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 月卡作业（每日）
 * </p>
 *
 * @author jobob
 * @since 2019-09-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CardJobPlanDaily extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户Id
     */
    private Long customId;

    /**
     * 员工Id
     */
    private Integer staffId;

    /**
     * openid
     */
    private String openId;

    /**
     * 剩余次/天数
     */
    private Integer remainNum;

    /**
     * 业务日期
     */
    private LocalDate busDate;

    /**
     * 处理状态
     */
    private JobStatus status;

    /**
     * 产品类型
     */
    private CardCategory category;

    /**
     * 状态可修改
     */
    private Boolean alterable;

    /**
     * 社区
     */
    private CommunityEnum community;

    /**
     * 地址
     */
    private String address;

}
