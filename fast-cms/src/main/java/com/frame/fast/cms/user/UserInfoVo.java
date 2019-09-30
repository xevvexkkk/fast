package com.frame.fast.cms.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.frame.fast.model.CommunityEnum;
import com.frame.fast.model.PersonInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserInfoVo  {

    private Long id;

    private String address;

    private String mobile;

    private String name;

    private String area;

    private CommunityEnum community;
    //新用户
    private Boolean noviceFlag;

    private String channelId;

    private String email;

    private String communityDesc ;

    public static List<UserInfoVo> tranFromUser(List<PersonInfo> personInfos){
        List<UserInfoVo> userInfoVos = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(personInfos)){
            personInfos.forEach(n->{
                UserInfoVo vo = new UserInfoVo();
                BeanUtils.copyProperties(n,vo);
                vo.setArea("嘉定区");
                userInfoVos.add(vo);
            });
        }
        return userInfoVos;
    }
}
