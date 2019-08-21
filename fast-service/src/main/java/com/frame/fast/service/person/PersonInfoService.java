package com.frame.fast.service.person;

import com.baomidou.mybatisplus.extension.service.IService;
import com.frame.fast.model.PersonInfo;

public interface PersonInfoService extends IService<PersonInfo> {
    PersonInfo getByOpenId(String openId);
}
