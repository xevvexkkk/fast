package com.frame.fast.service.person.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.fast.model.PersonInfo;
import com.frame.fast.repository.PersonInfoMapper;
import com.frame.fast.service.person.PersonInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PersonInfoServiceImpl extends ServiceImpl<BaseMapper<PersonInfo>,PersonInfo> implements PersonInfoService {

    @Resource
    private PersonInfoMapper personInfoMapper;

    @Override
    public PersonInfo getByOpenId(String openId) {
        if(StringUtils.isEmpty(openId)){
            return null;
        }
        QueryWrapper<PersonInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id",openId);
        return personInfoMapper.selectOne(wrapper);
    }
}
