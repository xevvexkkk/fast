package com.frame.fast.cms.user;

import com.frame.fast.model.MonthProduct;
import com.frame.fast.model.SingleProduct;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class SingleProductVo extends SingleProduct {


    private String productSortDesc;

    public static List<SingleProductVo> transFromOrigin(List<SingleProduct> monthProducts){
        List<SingleProductVo> monthProductVos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(monthProducts)){
            monthProducts.forEach(n->{
                SingleProductVo vo = new SingleProductVo();
                BeanUtils.copyProperties(n,vo);
                vo.setProductSortDesc(n.getProductSort().getName());
                monthProductVos.add(vo);
            });
        }
        return monthProductVos;
    }
}
