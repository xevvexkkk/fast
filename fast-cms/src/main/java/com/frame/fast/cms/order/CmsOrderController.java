package com.frame.fast.cms.order;

import com.frame.fast.model.Order;
import com.frame.fast.service.order.IOrderService;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/order")
@Controller
public class CmsOrderController {

    private Gson gson = new Gson();
    @Resource
    private IOrderService orderService;

    @GetMapping("/toList")
    public String toList(){
        return "order_list";
    }


    @GetMapping("/list")
    @ResponseBody
    public String list(){
        List<Order> list = orderService.list();
        return gson.toJson(OrderVo.tranFromOrigin(list));
    }
}
