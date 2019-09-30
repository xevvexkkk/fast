package com.frame.fast.cms.user;

import com.frame.fast.model.MonthProduct;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
@Data
public class MonthProductVo extends MonthProduct {

    public String statusDesc;

    public String categoryDesc;

    public static List<MonthProductVo> transFromOrigin(List<MonthProduct> monthProducts){
        List<MonthProductVo> monthProductVos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(monthProducts)){
            monthProducts.forEach(n->{
                MonthProductVo vo = new MonthProductVo();
                BeanUtils.copyProperties(n,vo);
                vo.setCategoryDesc(n.getCategory().getName());
                vo.setStatusDesc(n.getStatus().getName());
                monthProductVos.add(vo);
            });
        }
        return monthProductVos;
    }
}
