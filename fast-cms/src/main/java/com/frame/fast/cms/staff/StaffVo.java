package com.frame.fast.cms.staff;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.frame.fast.model.Staff;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class StaffVo extends Staff implements UserDetails {

    private String postDesc;

    public static List<StaffVo> tranFromOrigin(List<Staff> staffList){
        List<StaffVo> staffVos = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(staffList)){
            staffList.forEach(n->{
                StaffVo vo = new StaffVo();
                BeanUtils.copyProperties(n,vo);
                vo.setPostDesc(n.getPost().getName());
                staffVos.add(vo);
            });
        }
        return staffVos;
    }


}
