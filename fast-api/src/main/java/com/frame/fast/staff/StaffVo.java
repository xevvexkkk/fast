package com.frame.fast.staff;

import com.frame.fast.model.Staff;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class StaffVo extends Staff {

    private String postDesc;

    public StaffVo(){

    }

    public static StaffVo buildVo (Staff staff){
        StaffVo staffVo = new StaffVo();
        BeanUtils.copyProperties(staff,staffVo);
        staffVo.setPostDesc(staff.getPost().getName());
        return staffVo;
    }
}
