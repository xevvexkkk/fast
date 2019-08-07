package com.frame.fast.service.person.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.frame.fast.model.PersonInfo;
import com.frame.fast.service.person.PersonInfoService;
import org.springframework.stereotype.Service;

@Service
public class PersonInfoServiceImpl extends ServiceImpl<BaseMapper<PersonInfo>,PersonInfo> implements PersonInfoService {
}
