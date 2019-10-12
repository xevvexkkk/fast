package com.frame.fast.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 单次作业
 * </p>
 *
 * @author jobob
 * @since 2019-10-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SingleJobPlan extends BaseEntity {

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
    private boolean alterable;

    /**
     * 小区
     */
    private CommunityEnum community;

    /**
     * 地址
     */
    private String address;

    public static SingleJobPlan buildSingle(SingleProduct singleProduct,PersonInfo personInfo){
        SingleJobPlan singleJobPlan = new SingleJobPlan();
        singleJobPlan.setAddress(personInfo.getAddress());
        singleJobPlan.setCategory(CardCategory.getCategory(singleProduct.getProductSort()));
        singleJobPlan.setAlterable(true);
        singleJobPlan.setCommunity(personInfo.getCommunity());
        singleJobPlan.setOpenId(personInfo.getOpenId());
        singleJobPlan.setCustomId(personInfo.getId());
        singleJobPlan.setStatus(JobStatus.INIT);
        singleJobPlan.setRemainNum(1);
        return singleJobPlan;
    }
}
