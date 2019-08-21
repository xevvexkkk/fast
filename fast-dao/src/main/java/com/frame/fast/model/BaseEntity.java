package com.frame.fast.model;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
class BaseEntity {

    @TableId
    private Long id;

    /**
     * 创建日期
     */
    private LocalDateTime createAt;

    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 更新日期
     */
    private LocalDateTime updateAt;

    /**
     * 更新人
     */
    private Integer updateBy;
}
