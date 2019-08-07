package com.frame.fast.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.frame.fast.model.PersonInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PersonInfoMapper extends BaseMapper<PersonInfo> {
}
