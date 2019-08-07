package com.frame.fast.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("person_info")
public class PersonInfo {

    @TableId
    private Long id;

    private Integer sex;

    private String address;

    private String mobile;

    private String name;

    private String openId;

    private Integer area;

    private Integer community;
}
