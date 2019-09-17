package com.frame.fast.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 渠道
 * </p>
 *
 * @author jobob
 * @since 2019-08-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Channel extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 渠道名称
     */
    private String channelName;



}
